package org.orthodox.universel.compiler;

import org.beanplanet.core.io.IoException;
import org.beanplanet.core.io.resource.ByteArrayResource;
import org.beanplanet.core.io.resource.Resource;
import org.beanplanet.core.io.resource.StringResource;
import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.util.SizeUtil;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.orthodox.universal.parser.ParseException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.Script;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.UUID;

import static org.objectweb.asm.Opcodes.*;

public class UniversalCompiler {
    public Script parse(String compilationUnit) { return parse(new StringResource(compilationUnit)); }

    public Script parse(Resource compilationUnitResource) {
        try (Reader compilationUnitReader = compilationUnitResource.getReader()) {
            UniversalParser parser = new UniversalParser(compilationUnitReader);
            return parser.script();
        } catch (ParseException parseException) {
            throw new CompilationException(parseException);
        } catch (IOException ioEx) {
            throw new IoException(String.format("I/O error occurred during compilation [%s]: ", compilationUnitResource.getCanonicalForm()), ioEx);
        }
    }

    public CompiledUnit compile(String compilationUnit) {
        return compile(new StringResource(compilationUnit));
    }

    private CompiledUnit compile(Resource compilationUnitResource) {
        long startTime = System.currentTimeMillis();

        //----------------------------------------------------------------------------------------------------------
        // Parse the compilation unit to the topmost non-terminal.
        //----------------------------------------------------------------------------------------------------------
        Node compilationUnitNonTerminal = parse(compilationUnitResource);

        //----------------------------------------------------------------------------------------------------------
        // Compile to bytecode.
        //----------------------------------------------------------------------------------------------------------
        String classBasename = "UniScript"  + UUID.randomUUID().hashCode();
        String className = UniversalCompiler.class.getPackage().getName().replace('.','/')+"/"+classBasename;

        // Define top-level script class
        BytecodeHelper bch = new BytecodeHelper();
        ClassWriter cw = bch.generateClass(V1_8, ACC_PUBLIC, className, Object.class);

        // Generate execution method with binding
        MethodVisitor mv = bch.generateMethod(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "execute", Object.class, Map.class);

        CompilationContext compilationContext = new CompilationContext(mv, new VirtualMachine(bch));
        ScriptScope scriptScope = new ScriptScope();
        compilationContext.pushNameScope(scriptScope);

        CompilingAstVisitor compilingAstVisitor = new CompilingAstVisitor(compilationContext);
        scriptScope.setCompilationContext(compilationContext);
        compilationUnitNonTerminal.accept(compilingAstVisitor);

        if ( compilationContext.getVirtualMachine().operandStackIsEmpty() ) {
            mv.visitInsn(RETURN);
        } else {
            compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());
            mv.visitInsn(ARETURN);
        }

        mv.visitEnd();
        mv.visitMaxs(0,0);
        cw.visitEnd();

        long endTime = System.currentTimeMillis();
        byte classBytes[] = cw.toByteArray();
        return new CompiledUnit(compilationUnitNonTerminal,
                                compilationContext.getMessages(),
                                new ByteArrayResource(classBytes),
                                className,
                                classBasename,
                                endTime-startTime);
    }

    public static void main(String... args) throws Exception {
        String script = args[0];
        UniversalCompiler compiler = new UniversalCompiler();
        CompiledUnit compiledUnit = compiler.compile(script);

        MyClassLoader classLoader = new MyClassLoader();
        Class aClass = classLoader.defineClass(compiledUnit.getFullyQualifiedName().replace('/','.'), compiledUnit.getCode().readFullyAsBytes());
        Object result = TypeUtil.invokeStaticMethod(aClass, "main");
        System.out.println("Compilation completed in: "+ SizeUtil.getElapsedTimeSpecificationDescription(compiledUnit.getCompilationTime())+" result="+result);
    }

    private static class MyClassLoader extends ClassLoader {
        public Class defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }
}

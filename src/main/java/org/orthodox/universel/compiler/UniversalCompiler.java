package org.orthodox.universel.compiler;

import org.objectweb.asm.Type;
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
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.literals.BooleanLiteralExpr;

import java.io.IOException;
import java.io.Reader;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static org.objectweb.asm.Opcodes.*;

public class UniversalCompiler {
    private static AtomicLong inc = new AtomicLong();
    private VirtualMachine virtualMachine = new VirtualMachine();

    public CompiledUnit compile(String script) {
        return compile(new StringResource(script));
    }

    private CompiledUnit compile(Resource compilationUnitResource) {
        try (Reader compilationUnitReader = compilationUnitResource.getReader()) {
            long startTime = System.currentTimeMillis();

            //----------------------------------------------------------------------------------------------------------
            // Parse the compilation unit to the topmost non-terminal.
            //----------------------------------------------------------------------------------------------------------
            UniversalParser parser = new UniversalParser(compilationUnitReader);
            Node compilationUnitNonTerminal = parser.Literal();

            //----------------------------------------------------------------------------------------------------------
            // Compile to bytecode.
            //----------------------------------------------------------------------------------------------------------
            String classBasename = "UniScript"  + UUID.randomUUID().hashCode();
            String className = UniversalCompiler.class.getPackage().getName().replace('.','/')+"/"+classBasename;

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
            cw.visit(V1_8, ACC_PUBLIC, className, null, "java/lang/Object", null);
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "main", "()" + Type.getDescriptor(Object.class), null, null);
            mv.visitCode();

            CompilationContext compilationContext = new CompilationContext(mv, new VirtualMachine());
            CompilingAstVisitor compilingAstVisitor = new CompilingAstVisitor(compilationContext);
            compilationUnitNonTerminal.accept(compilingAstVisitor);

            compilationContext.getBytecodeHelper().box(compilationContext.getVirtualMachine().peekOperandStack());
            mv.visitInsn(ARETURN);

            mv.visitEnd();
            mv.visitMaxs(0,0);
            cw.visitEnd();

            long endTime = System.currentTimeMillis();
            return new CompiledUnit(new ByteArrayResource(cw.toByteArray()), className, classBasename, endTime-startTime);
        } catch (ParseException parseException) {
            throw new CompilationException(parseException);
        } catch (IOException ioEx) {
            throw new IoException(String.format("I/O error occurred during compilation [%s]: ", compilationUnitResource.getCanonicalForm()), ioEx);
        }
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

package org.orthodox.universel.compiler;

import org.beanplanet.core.io.IoException;
import org.beanplanet.core.io.resource.ByteArrayResource;
import org.beanplanet.core.io.resource.Resource;
import org.beanplanet.core.io.resource.StringResource;
import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.util.SizeUtil;
import org.beanplanet.messages.domain.Messages;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.orthodox.universal.parser.ParseException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.conversion.CstTransformer;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.Script;
import org.orthodox.universel.symanticanalysis.MethodCallAnalyser;
import org.orthodox.universel.symanticanalysis.SemanticAnalyser;
import org.orthodox.universel.symanticanalysis.SemanticAnalysisContext;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.beanplanet.messages.domain.MessagesImpl.messages;
import static org.objectweb.asm.Opcodes.*;

public class UniversalCompiler {
    private static final CstTransformer cstTransformer = new CstTransformer();

    private static final List<SemanticAnalyser> SEMANTIC_AANALYSERS = asList(
            new MethodCallAnalyser()
    );

    public Script parse(String compilationUnit) { return parse(new StringResource(compilationUnit)); }

    public Script parse(Resource compilationUnitResource) {
        try (Reader compilationUnitReader = compilationUnitResource.getReader()) {
            //----------------------------------------------------------------------------------------------------------
            // Lexical Analysis:
            // Parse the source resource.
            //----------------------------------------------------------------------------------------------------------
            UniversalParser parser = new UniversalParser(compilationUnitReader);
            Script script = parser.script();
            return script;

            //----------------------------------------------------------------------------------------------------------
            // Conversion:
            // Transform the CST into an AST
            //----------------------------------------------------------------------------------------------------------
//            return (Script)script.accept(cstTransformer);
        } catch (ParseException parseException) {
            throw new CompilationParseException(parseException);
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
        // Lexical Analysis:
        // Conversion:
        // Parse the compilation unit to the topmost non-terminal.
        //----------------------------------------------------------------------------------------------------------
        Node compilationUnitNonTerminal = parse(compilationUnitResource);

        //----------------------------------------------------------------------------------------------------------
        // Semantic Analysis:
        // Iterate over the parse tree, created from the Lexical Analysis phase and perform various transformations.
        //----------------------------------------------------------------------------------------------------------
        final Messages messages = messages();
        SemanticAnalysisContext semanticAnalysisContext = new SemanticAnalysisContext(messages);
        for (SemanticAnalyser semanticAnalyser : SEMANTIC_AANALYSERS) {
            semanticAnalyser.performAnalysis(semanticAnalysisContext, compilationUnitNonTerminal);
        }

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

        CompilationContext compilationContext = new CompilationContext(mv, new VirtualMachine(bch), messages);
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

package org.orthodox.universel.compiler;

import org.beanplanet.core.io.IoException;
import org.beanplanet.core.io.resource.Resource;
import org.beanplanet.core.io.resource.StringResource;
import org.beanplanet.messages.domain.Messages;
import org.orthodox.universal.parser.ParseException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.Script;
import org.orthodox.universel.cst.Type;
import org.orthodox.universel.exec.navigation.ConcurrentNavigatorRegistry;
import org.orthodox.universel.exec.navigation.NavigatorLoader;
import org.orthodox.universel.exec.navigation.NavigatorRegistry;
import org.orthodox.universel.exec.navigation.PackageScanNavigatorLoader;
import org.orthodox.universel.symanticanalysis.*;
import org.orthodox.universel.symanticanalysis.conversion.BinaryExpressionOperatorMethodConverter;
import org.orthodox.universel.symanticanalysis.conversion.WideningNumericConversionAnalyser;
import org.orthodox.universel.symanticanalysis.methods.ImplicitMethodModifiersDecorator;
import org.orthodox.universel.symanticanalysis.methods.ImplicitReturnStatementDecorator;
import org.orthodox.universel.symanticanalysis.methods.UnresolvedMethodCallReporter;
import org.orthodox.universel.symanticanalysis.name.IfStatementImplicitResultValueResolver;
import org.orthodox.universel.symanticanalysis.name.NavigationResolver;

import java.io.IOException;
import java.io.Reader;

import static org.beanplanet.messages.domain.MessagesImpl.messages;

public class UniversalCompiler {
    private static NavigatorRegistry navigatorRegistry = new ConcurrentNavigatorRegistry();
    private static NavigatorLoader loader = new PackageScanNavigatorLoader();

    public UniversalCompiler() {
        loader.load(navigatorRegistry);
    }

    private SemanticAnalyser getSemanticAnalyser() {
        return new CompositeSemanticAnalyser(
            new MethodDeclarationDeclaringTypeDecorator(),
            new ScriptAssembler(),
            new BinaryExpressionOperatorMethodConverter(),
//            new MethodCallAnalyser(),
//            new ConditionalExpressionAnalyser(),
            new TypeReferenceResolver(),
            new ImplicitMethodModifiersDecorator(),
            new WideningNumericConversionAnalyser(),
            new NodeSequenceLastValueAnalyser(),
//            new NavigationContextResolver(),
            new NavigationResolver(),
            new IfStatementImplicitResultValueResolver(),
            new UnresolvedMethodCallReporter(),
            new ImplicitReturnStatementDecorator()
        );
    }

    private <T> Node parsePhases(CompilationContext compilationContext, Resource compilationUnitResource, Class<T> bindingType, Messages messages) {
        try (Reader compilationUnitReader = compilationUnitResource.getReader()) {
            //----------------------------------------------------------------------------------------------------------
            // Lexical Analysis:
            // Parse the source resource.
            //----------------------------------------------------------------------------------------------------------
            UniversalParser parser = new UniversalParser(compilationUnitReader);
            Script script;
            try {
                script = parser.script();
            } catch (ParseException ex) {
                messages.addError(ex, "uel.syntax", ex.getMessage().replace("{", "'{'"));
                return null;
            }

            //----------------------------------------------------------------------------------------------------------
            // Semantic Analysis:
            // Perform widening conversions, where applicable and realise types depth-first, post-order.
            //----------------------------------------------------------------------------------------------------------
            SemanticAnalysisContext semanticAnalysisContext = new SemanticAnalysisContext(compilationContext.getDefaultImports(), bindingType, messages, navigatorRegistry);
            semanticAnalysisContext.pushScope(new ScriptScope(bindingType, navigatorRegistry));
            Node node = getSemanticAnalyser().performAnalysis(semanticAnalysisContext, script);

            return node;
        } catch (IOException ioEx) {
            throw new IoException(String.format("I/O error occurred during compilation [%s]: ", compilationUnitResource.getCanonicalForm()), ioEx);
        }
    }

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
        } catch (ParseException parseException) {
            throw new CompilationParseException(parseException);
        } catch (IOException ioEx) {
            throw new IoException(String.format("I/O error occurred during compilation [%s]: ", compilationUnitResource.getCanonicalForm()), ioEx);
        }
    }

    public CompiledUnit<?> compile(String compilationUnit) {
        return compile(new StringResource(compilationUnit));
    }

    public <B> CompiledUnit<B> compile(String compilationUnit, Class<B> bindingType) {
        return compile(new StringResource(compilationUnit), bindingType);
    }

    public CompiledUnit<?> compile(Resource compilationUnitResource) {
        return compile(compilationUnitResource, (Class<?>)null);
    }

    public <B> CompiledUnit<B> compile(Resource compilationUnitResource, Class<B> bindingType) {
        final Messages messages = messages();
        CompilationContext compilationContext = new CompilationContext(bindingType, new VirtualMachine(new BytecodeHelper()), messages);

        //----------------------------------------------------------------------------------------------------------
        // Analysis: Lexical and Semantic
        //----------------------------------------------------------------------------------------------------------
        long startTime = System.currentTimeMillis();
        Script compilationUnitNonTerminal = (Script)parsePhases(compilationContext, compilationUnitResource, bindingType, messages);
        long endTime = System.currentTimeMillis();

        //----------------------------------------------------------------------------------------------------------
        // If there are no errors compile to bytecode.
        //----------------------------------------------------------------------------------------------------------
//        byte[] classBytes = null;
//        final String classBasename = "UniScript" + UUID.randomUUID().hashCode();
//        NamePath className = null;
        if ( !messages.hasErrors() ) {
//            final NamePath pkgName = compilationUnitNonTerminal.getPackageDeclaration() != null ? compilationUnitNonTerminal.getPackageDeclaration().getName() : NamePath.EMPTY_NAME_PATH;
//            className = pkgName.joinSingleton(classBasename);
//            final String fqClassName = className.join("/");
//
//            // Define top-level script class
//            BytecodeHelper bch = new BytecodeHelper();
//            ClassWriter cw = bch.generateClass(V1_8, ACC_PUBLIC, fqClassName, Object.class);
//
//            // Generate execution method with binding
//                MethodVisitor mv = bch.generateMethod(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "exec", Object.class, bindingType == null ? Object.class : bindingType);

//            CompilationContext compilationContext = new CompilationContext(bindingType, new VirtualMachine(bch), messages);
            BoundScope scriptScope = new ScriptScope(bindingType, navigatorRegistry);
            compilationContext.pushNameScope(scriptScope);

            CompilingAstVisitor compilingAstVisitor = new CompilingAstVisitor(compilationContext);
            compilationUnitNonTerminal.accept(compilingAstVisitor);

//            if (compilationContext.getVirtualMachine().operandStackIsEmpty()) {
//                bch.emitLoadNullOperand();
//                mv.visitInsn(ARETURN);
//            } else {
//                compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());
//                mv.visitInsn(ARETURN);
//            }
//
//            mv.visitEnd();
//            mv.visitMaxs(0, 0);
//            cw.visitEnd();

            endTime = System.currentTimeMillis();
//            classBytes = cw.toByteArray();
        }

        return new CompiledUnit<>(bindingType,
                                  compilationUnitNonTerminal,
                                  messages,
                                  compilationContext.getCompiledClassResources(),
                                  endTime - startTime
        );
    }

    @SuppressWarnings("inchecked")
    public <B> CompiledUnit<B> compile(Resource compilationUnitResource, Type bindingType) {
        return (CompiledUnit<B>)compile(compilationUnitResource, Object.class);
    }

    private static class MyClassLoader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }
}

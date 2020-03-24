package org.orthodox.universel;

import org.beanplanet.core.io.resource.Resource;
import org.beanplanet.core.io.resource.StringResource;
import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.logging.BeanpanetLoggerFactory;
import org.beanplanet.core.logging.Logger;
import org.orthodox.universel.compiler.CompilationErrorsException;
import org.orthodox.universel.compiler.CompiledUnit;
import org.orthodox.universel.compiler.UniversalCompiler;
import org.orthodox.universel.cst.ImportDecl;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.ParseTree;
import org.orthodox.universel.cst.Script;

import java.util.Collections;
import java.util.Map;

import static java.lang.String.format;

/**
 * Helper class for parsing and executing Universal expressions and scripts.
 */
public class Universal implements Logger {
    private static final Logger LOG = BeanpanetLoggerFactory.getSystemLoggerFor(Universal.class);

    public static final Script parse(String script) {
        long startTimeMillis = System.currentTimeMillis();
//        LOG.info("Start parse of script [{0}] ...", script);
        UniversalCompiler compiler = new UniversalCompiler();
        Script scriptTerminal = compiler.parse(script);
        long endTimeMillis = System.currentTimeMillis();
//        LOG.info("End parse of script [{0}] in {1}", script, SizeUtil.getElapsedTimeSpecificationDescription(endTimeMillis-startTimeMillis));
        return scriptTerminal;
    }

    @SuppressWarnings("unchecked")
    public static final <T extends Node> T parse(Class<T> astType, String script) {
        Script scriptNode = parse(script);

        if (astType == ImportDecl.class && scriptNode.getImportDeclaration() != null) return (T)scriptNode.getImportDeclaration();
        return new ParseTree(scriptNode).preorderStream()
                                        .filter(n -> astType.isAssignableFrom(n.getClass()))
                                        .map(n -> (T)n)
                                        .findFirst()
                                        .orElseThrow(() -> new UniversalException(format("Unable to parse expression [%s] to single given AST node of tyoe [%s]: found %d nodes when expecting 1",
                                                                                         script,
                                                                                         astType,
                                                                                         scriptNode.getBodyElements().size())));
//        return nullSafe(scriptNode.getBodyElements()).stream()
//                  .filter(n -> astType.isAssignableFrom(n.getClass()))
//                  .map(n -> (T)n)
//                  .findFirst()
//                  .orElseThrow(() -> new UniversalException(format("Unable to parse expression [%s] to single given AST node of tyoe [%s]: found %d nodes when expecting 1",
//                                                             script,
//                                                             astType,
//                                                             scriptNode.getBodyElements().size())));
    }

    /**
     * Evaluates a script and executes it immediately. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script the script to be executed.
     * @return any result returned by the script, which may be null, or null if h script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public static <T> T execute(String script) {
        return execute(script, Collections.emptyMap());
    }

    /**
     * Evaluates a script and executes it immediately. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script the script resource to be executed.
     * @return any result returned by the script, which may be null, or null if h script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public static <T> T execute(Resource script) {
        return execute(script, Collections.emptyMap());
    }

    /**
     * Evaluates a script and executes it immediately. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script the script to be executed.
     * @param resultType the type of the result expected from execution of the script: either directly or through type
     *                   conversion applied to the result.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public static <T> T execute(Class<T> resultType, String script) {
        return execute(script, Collections.emptyMap());
    }

    /**
     * Evaluates a script and executes it immediately. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script the script resource to be executed.
     * @param resultType the type of the result expected from execution of the script: either directly or through type
     *                   conversion applied to the result.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public static <T> T execute(Class<T> resultType, Resource script) {
        return execute(script, Collections.emptyMap());
    }

    /**
     * Evaluates a script and executes it immediately using the supplied map binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script the script to be executed.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <T> T execute(String script, Map<String, Object> binding) {
        return execute(new StringResource(script), binding)
;    }

    /**
     * Evaluates a script and executes it immediately using the supplied map binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script the script resource to be executed.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public static <T> T execute(Resource script, Map<String, Object> binding) {
        return (T)execute(Object.class, script, binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied map binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script the script to be executed.
     * @param resultType the type of the result expected from execution of the script: either directly or through type
     *                   conversion applied to the result.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <T> T execute(Class<T> resultType, String script, Map<String, Object> binding) {
        return execute(resultType, new StringResource(script), binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied map binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script the script resource to be executed.
     * @param resultType the type of the result expected from execution of the script: either directly or through type
     *                   conversion applied to the result.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public static <T> T execute(Class<T> resultType, Resource script, Map<String, Object> binding) {
        CompiledUnit compiledUnit = compile(script);

        if (compiledUnit.getMessages().hasErrors()) {
            throw new CompilationErrorsException(compiledUnit.getMessages());
        }
        MyClassLoader classLoader = new MyClassLoader();

        Class aClass = classLoader.defineClass(compiledUnit.getFullyQualifiedName().replace('/','.'), compiledUnit.getCode().readFullyAsBytes());
        Object result = TypeUtil.invokeStaticMethod(aClass, "exec", binding);
        return (T)result;
    }

    /**
     * Compiles the given script, returning the {@link CompiledUnit}.
     *
     * @param script the script to be compiled.
     * @return the compiled unit which includes any compilation messages or erross and a
     * reference to the compiled bytecode if compilation succeeded.
     */
    public static CompiledUnit compile(String script) {
        UniversalCompiler compiler = new UniversalCompiler();
        return compiler.compile(script);
    }

    /**
     * Compiles the given script, returning the {@link CompiledUnit}.
     *
     * @param script the script resource to be compiled.
     * @return the compiled unit which includes any compilation messages or erross and a
     * reference to the compiled bytecode if compilation succeeded.
     */
    public static CompiledUnit compile(Resource script) {
        UniversalCompiler compiler = new UniversalCompiler();
        CompiledUnit compiledUnit = compiler.compile(script);

        return compiledUnit;
    }

    private static class MyClassLoader extends ClassLoader {
        public Class defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }
}

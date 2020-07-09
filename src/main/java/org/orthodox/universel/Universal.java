package org.orthodox.universel;

import org.beanplanet.core.io.resource.Resource;
import org.beanplanet.core.io.resource.StringResource;
import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.logging.Logger;
import org.orthodox.universel.compiler.CompilationErrorsException;
import org.orthodox.universel.compiler.CompiledUnit;
import org.orthodox.universel.compiler.UniversalCompiler;
import org.orthodox.universel.cst.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * Helper class for parsing and executing Universal expressions and scripts.
 */
public class Universal implements Logger {
    public static final UniversalCompiler COMPILER = new UniversalCompiler();

    public static Script parse(String script) {
        return COMPILER.parse(script);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Node> T parse(Class<T> astType, String script) {
        Script scriptNode = parse(script);

        if (astType == ImportDecl.class && scriptNode.getImportDeclaration() != null) return (T) scriptNode.getImportDeclaration();
        return new ParseTree(scriptNode).preorderStream()
                                        .filter(n -> astType.isAssignableFrom(n.getClass()))
                                        .map(n -> (T) n)
                                        .findFirst()
                                        .orElseThrow(() -> new UniversalException(format("Unable to parse expression [%s] to single given AST node of tyoe [%s]: found %d nodes when expecting 1",
                                                                                         script,
                                                                                         astType,
                                                                                         scriptNode.getBodyElements().size()
                                        )));
    }

    /**
     * Evaluates a script and executes it immediately. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script the script to be executed.
     * @return any result returned by the script, which may be null, or null if h script did not return any result.
     */
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
    public static <T> T execute(Resource script) {
        return execute(script, Collections.emptyMap());
    }

    /**
     * Evaluates a script and executes it immediately. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script     the script to be executed.
     * @param resultType the type of the result expected from execution of the script: either directly or through type
     *                   conversion applied to the result.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <T> T execute(Class<T> resultType, String script) {
        return execute(resultType, new StringResource(script));
    }

    /**
     * Evaluates a script and executes it immediately. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script     the script resource to be executed.
     * @param resultType the type of the result expected from execution of the script: either directly or through type
     *                   conversion applied to the result.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <T> T execute(Class<T> resultType, Resource script) {
        return execute(resultType, script, null);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script  the script to be executed.
     * @param binding the binding associated with script compilation and execution.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <B, T> T execute(String script, B binding) {
        return execute(new StringResource(script), binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding type and binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script      the script to be executed.
     * @param bindingType the type of binding on which the compiled unit will be based, which may not be null.
     * @param binding     the binding associated with script compilation and execution, which must be an instance of the given binding type.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <B, T> T execute(String script, Type bindingType, B binding) {
        return execute(new StringResource(script), bindingType, binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script      the script to be executed.
     * @param bindingType the type of binding on which the compiled unit will be based, which may not be null.
     * @param binding     the binding associated with script compilation and execution.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <B, T> T execute(String script, Class<B> bindingType, B binding) {
        return execute(new StringResource(script), bindingType, binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied map binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script  the script to be executed.
     * @param binding the binding associated with script compilation and execution.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <T> T execute(String script, Map<String, Object> binding) {
        return execute(new StringResource(script), binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied map binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script  the script resource to be executed.
     * @param binding the binding associated with script compilation and execution.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public static <T> T execute(Resource script, Map<String, Object> binding) {
        return (T) execute(Object.class, script, binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script  the script resource to be executed.
     * @param binding the binding associated with script compilation and execution.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public static <B, T> T execute(Resource script, B binding) {
        return (T) execute(Object.class, script, binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script      the script resource to be executed.
     * @param bindingType the type of binding on which the compiled unit will be based, which may not be null.
     * @param binding     the binding associated with script compilation and execution, which must be an instance of the given binding type.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public static <B, T> T execute(Resource script, Type bindingType, B binding) {
        return (T) execute(Object.class, script, bindingType, binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script      the script resource to be executed.
     * @param bindingType the type of binding on which the compiled unit will be based, which may not be null.
     * @param binding     the binding associated with script compilation and execution.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public static <B, T> T execute(Resource script, Class<B> bindingType, B binding) {
        return (T) execute(Object.class, script, bindingType, binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied map binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script     the script to be executed.
     * @param binding    the binding associated with script compilation and execution.
     * @param resultType the type of the result expected from execution of the script: either directly or through type
     *                   conversion applied to the result.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <T> T execute(Class<T> resultType, String script, Map<String, Object> binding) {
        return execute(resultType, new StringResource(script), binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script     the script to be executed.
     * @param binding    the binding associated with script compilation and execution.
     * @param resultType the type of the result expected from execution of the script: either directly or through type
     *                   conversion applied to the result.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <B, T> T execute(Class<T> resultType, String script, B binding) {
        return execute(resultType, new StringResource(script), binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script      the script to be executed.
     * @param bindingType the type of binding on which the compiled unit will be based, which may not be null.
     * @param binding     the binding associated with script compilation and execution, which must be an instance of the given binding type.
     * @param resultType  the type of the result expected from execution of the script: either directly or through type
     *                    conversion applied to the result.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <B, T> T execute(Class<T> resultType, String script, Type bindingType, B binding) {
        return execute(resultType, new StringResource(script), bindingType, binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script      the script to be executed.
     * @param bindingType the type of binding on which the compiled unit will be based, which may not be null.
     * @param binding     the binding associated with script compilation and execution.
     * @param resultType  the type of the result expected from execution of the script: either directly or through type
     *                    conversion applied to the result.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <B, T> T execute(Class<T> resultType, String script, Class<B> bindingType, B binding) {
        return execute(resultType, new StringResource(script), bindingType, binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script     the script resource to be executed.
     * @param binding    the binding associated with script compilation and execution, which may be null.
     * @param resultType the type of the result expected from execution of the script: either directly or through type
     *                   conversion applied to the result.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public static <B, T> T execute(Class<T> resultType, Resource script, B binding) {
        return execute(resultType, script, binding == null ? null : (Class<B>) binding.getClass(), binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param resultType  the type of the result expected from execution of the script: either directly or through type
     *                    conversion applied to the result.
     * @param script      the script resource to be executed.
     * @param bindingType the type of binding on which the compiled unit will be based, which may not be null.
     * @param binding     the binding associated with script compilation and execution, which must be an instance of the given binding type.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <B, T> T execute(Class<T> resultType, Resource script, Type bindingType, B binding) {
        return execute(resultType, compile(script, bindingType), binding);
    }

    /**
     * Evaluates a script and executes it immediately using the supplied binding. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param resultType  the type of the result expected from execution of the script: either directly or through type
     *                    conversion applied to the result.
     * @param script      the script resource to be executed.
     * @param bindingType the type of binding on which the compiled unit will be based, which may not be null.
     * @param binding     the binding associated with script compilation and execution, which may be null.
     * @return any result returned by the script, which may be null, or null if the script did not return any result.
     */
    public static <B, T> T execute(Class<T> resultType, Resource script, Class<B> bindingType, B binding) {
        return execute(resultType, compile(script, bindingType), binding);
    }

    /**
     * Executes a compiled script using the compatible binding specified.
     *
     * @param resultType the type of the result expected from execution of the script: either directly or through type
     *                   conversion applied to the result.
     * @param script     the compiled script, previously obtained via a call to <code>execute</code>.
     * @return the result returned by the script, converted to the requested result type if necessary.
     */
    public static <B, T> T execute(Class<T> resultType, CompiledUnit<B> script) {
        return execute(resultType, script, null);
    }

    /**
     * Executes a compiled script using the compatible binding specified.
     *
     * @param script  the compiled script, previously obtained via a call to <code>execute</code>.
     * @param binding the binding to be associated with execution, which may be null.
     * @return the result returned by the script, converted to the requested result type if necessary.
     */
    @SuppressWarnings("unchecked")
    public static <B, T> T execute(CompiledUnit<B> script, B binding) {
        return execute((Class<T>) Object.class, script, binding);
    }

    /**
     * Executes a compiled script using the compatible binding specified.
     *
     * @param resultType the type of the result expected from execution of the script: either directly or through type
     *                   conversion applied to the result.
     * @param script     the compiled script, previously obtained via a call to <code>execute</code>.
     * @param binding    the binding to be associated with execution, which may be null.
     * @return the result returned by the script, converted to the requested result type if necessary.
     */
    @SuppressWarnings("unchecked")
    public static <B, T> T execute(Class<T> resultType, CompiledUnit<B> script, B binding) {
        if (script.getMessages().hasErrors()) {
            throw new CompilationErrorsException(script.getMessages());
        }

        List<Class<?>> compiledClasses = script.getCompiledClasses();
        if (compiledClasses.size() != 1) {
            throw new IllegalStateException("It looks like the script compiled is not executable? Were there excecutable statements specified?");
        }

        Object result = TypeUtil.invokeStaticMethod(compiledClasses.get(0), "main", script.getBindingType() != null ? new Object[] { binding } : null);
        return (T) result;
    }

    /**
     * Compiles the given script, returning the {@link CompiledUnit}.
     *
     * @param script the script to be compiled.
     * @return the compiled unit which includes any compilation messages or erross and a
     * reference to the compiled bytecode if compilation succeeded.
     */
    public static CompiledUnit<?> compile(String script) {
        UniversalCompiler compiler = new UniversalCompiler();
        return compiler.compile(script);
    }

    /**
     * Compiles the given script, returning the {@link CompiledUnit}.
     *
     * @param script      the script to be compiled.
     * @param bindingType the type of binding on which the compiled unit will be based, which may not be null.
     * @return the compiled unit which includes any compilation messages or erross and a
     * reference to the compiled bytecode if compilation succeeded.
     */
    public static <B> CompiledUnit<B> compile(String script, Type bindingType) {
        return compile(new StringResource(script), bindingType);
    }

    /**
     * Compiles the given script, returning the {@link CompiledUnit}.
     *
     * @param script      the script to be compiled.
     * @param bindingType the type of binding on which the compiled unit will be based.
     * @return the compiled unit which includes any compilation messages or erross and a
     * reference to the compiled bytecode if compilation succeeded.
     */
    public static <B> CompiledUnit<B> compile(String script, Class<B> bindingType) {
        return compile(new StringResource(script), bindingType);
    }

    /**
     * Compiles the given script, returning the {@link CompiledUnit}.
     *
     * @param script the script resource to be compiled.
     * @return the compiled unit which includes any compilation messages or erross and a
     * reference to the compiled bytecode if compilation succeeded.
     */
    public static CompiledUnit<?> compile(Resource script) {
        return compile(script, (Class<?>)null);
    }

    /**
     * Compiles the given script, returning the {@link CompiledUnit}.
     *
     * @param script      the script resource to be compiled.
     * @param bindingType the type of binding on which the compiled unit will be based.
     * @return the compiled unit which includes any compilation messages or erross and a
     * reference to the compiled bytecode if compilation succeeded.
     */
    public static <B> CompiledUnit<B> compile(Resource script, Type bindingType) {
        return COMPILER.compile(script, bindingType);
    }

    /**
     * Compiles the given script, returning the {@link CompiledUnit}.
     *
     * @param script      the script resource to be compiled.
     * @param bindingType the type of binding on which the compiled unit will be based.
     * @return the compiled unit which includes any compilation messages or erross and a
     * reference to the compiled bytecode if compilation succeeded.
     */
    public static <B> CompiledUnit<B> compile(Resource script, Class<B> bindingType) {
        return COMPILER.compile(script, bindingType);
    }
}

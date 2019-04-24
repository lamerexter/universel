package org.orthodox.universel;

import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.logging.Logger;
import org.beanplanet.core.util.SizeUtil;
import org.orthodox.universel.compiler.CompiledUnit;
import org.orthodox.universel.compiler.UniversalCompiler;

/**
 * Helper class for parsing and executing Universal expressions and scripts.
 */
public class Universal implements Logger {
    /**
     * Evaluates a script and executes it immediately. The script will be parsed, compiled to bytecode and then executed
     * by this invocation.
     *
     * @param script the script to be executed.
     * @return any result returned by the script, which may be null, or null if h script did not return any result.
     */
    @SuppressWarnings("unchecked")
    public <T> T eval(String script) {
        info("Beginning evaluation of script [{0}] ...", script);
        UniversalCompiler compiler = new UniversalCompiler();
        CompiledUnit compiledUnit = compiler.compile(script);
        info("Compilation of script [{0}] completed", script);

        MyClassLoader classLoader = new MyClassLoader();

        info("Beginning execution of script [{0}] ...", script);
        Class aClass = classLoader.defineClass(compiledUnit.getFullyQualifiedName().replace('/','.'), compiledUnit.getCode().readFullyAsBytes());
        Object result = TypeUtil.invokeStaticMethod(aClass, "main");
        info("Completed execution of script [{0}] in {1}", script, SizeUtil.getElapsedTimeSpecificationDescription(compiledUnit.getCompilationTime()));
        return (T)result;
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
        return new Universal().eval(script);
    }

    private static class MyClassLoader extends ClassLoader {
        public Class defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }
}

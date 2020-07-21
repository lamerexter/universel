/*
 *  MIT Licence:
 *
 *  Copyright (c) 2020 Orthodox Engineering Ltd
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software,
 *  and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 *  KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *  PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *  CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 *  DEALINGS IN THE SOFTWARE.
 *
 */
package org.orthodox.universel.exec.operators.binary;

import org.beanplanet.core.io.IoUtil;
import org.beanplanet.core.lang.FilteringPackageClassScanner;
import org.beanplanet.core.lang.PackageResourceScanner;
import org.beanplanet.core.lang.conversion.TypeConversionException;
import org.beanplanet.core.logging.Logger;
import org.orthodox.universel.ast.Operator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

import static java.lang.reflect.Modifier.isPublic;
import static org.beanplanet.core.lang.TypeUtil.getMethodsInClassHierarchy;
import static org.beanplanet.core.util.CollectionUtil.nullSafe;
import static org.beanplanet.core.util.StringUtil.asCsvList;
import static org.beanplanet.core.util.StringUtil.asDelimitedString;
import static org.orthodox.universel.ast.Modifiers.isStatic;

public class PackageScanBinaryOperatorLoader implements BinaryOperatorLoader, Logger {
    public static final String TYPE_CONVERTER_PACKAGES_RESOURCE = "META-INF/services/org/orthodox/universal/binary-operator-packages.txt";

    protected String binaryOPeratorPackagesResource = TYPE_CONVERTER_PACKAGES_RESOURCE;

    public PackageScanBinaryOperatorLoader() {
    }

    public PackageScanBinaryOperatorLoader(final String binaryOPeratorPackagesResource) {
        this.binaryOPeratorPackagesResource = binaryOPeratorPackagesResource;
    }

    protected static final Predicate<Class<?>> ANNOTATED_CLASS_FILTER = new Predicate<Class<?>>() {
        public boolean test(Class<?> clazz) {
            return clazz.isAnnotationPresent(BinaryOperator.class);
        }
    };
    protected static final Predicate<Class<?>> PACKAGE_SCAN_CLASS_FILTER = ANNOTATED_CLASS_FILTER;

    protected PackageResourceScanner<Class<?>> packageScanner = new FilteringPackageClassScanner(PACKAGE_SCAN_CLASS_FILTER);

    private static final Predicate<Method> ANNOTATED_METHOD_FILTER = new Predicate<Method>() {
        public boolean test(Method method) {
            Class<?> paramTypes[] = method.getParameterTypes();
            return isPublic(method.getModifiers())
                   && isStatic(method.getModifiers())
                   && !Modifier.isAbstract(method.getModifiers())
                   && method.isAnnotationPresent(BinaryOperator.class)
                   && (paramTypes.length == 2 || (paramTypes.length == 3 && Operator.class.isAssignableFrom(paramTypes[2])));
        }
    };

    /**
     * @return the binaryOPeratorPackagesResource
     */
    public String getBinaryOpetratorrPackagesResource() {
        return binaryOPeratorPackagesResource;
    }

    /**
     * @param binaryOPeratorPackagesResource the binaryOPeratorPackagesResource to set
     */
    public void setBinaryOPeratorPackagesResource(String binaryOPeratorPackagesResource) {
        this.binaryOPeratorPackagesResource = binaryOPeratorPackagesResource;
    }

    public void load(BinaryOperatorRegistry registry) {
        // ------------------------------------------------------------------------
        // Add context class loader packages first, if available
        // ------------------------------------------------------------------------
        String packageNames[] = findPackageNames();
        if (packageNames.length > 0) {
            if (isDebugEnabled()) {
                debug("Discovered binary operator " + (packageNames.length == 1 ? "package" : "packages") + ": \n" + asDelimitedString(packageNames, ",\n"));
            }

            Set<Class<?>> classes = packageScanner.findResourcesInPackages(packageNames);
            loadBinaryOperators(registry, classes);

            if (isDebugEnabled()) {
                debug("Loaded " + registry.size() + " binary operators from " + classes.size() + (classes.size() == 1 ? " class in " : " classes in ") + packageNames.length + (packageNames.length == 1 ? " package " : " packages ") + "into registry");
            }
        }
    }

    protected String[] findPackageNames() {
        Set<String> packageNamesSet = new LinkedHashSet<String>();
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader classLoader = getClass().getClassLoader();
        if (contextClassLoader != null) {
            findAndAddPackages(packageNamesSet, contextClassLoader);
        }
        if (contextClassLoader != classLoader) {
            findAndAddPackages(packageNamesSet, classLoader);
        }
        return packageNamesSet.toArray(new String[packageNamesSet.size()]);
    }

    protected void findAndAddPackages(Set<String> packageNames, ClassLoader cl) {
        if (isDebugEnabled()) {
            debug("Searching for annotation-configured binary operators [classLoader=" + cl + ", resource(s)=" + getBinaryOpetratorrPackagesResource() + "] ...");
        }

        BufferedReader reader = null;
        try {
            for (String resourcePath : nullSafe(asCsvList(getBinaryOpetratorrPackagesResource()))) {
                for (Enumeration<URL> resources = cl.getResources(resourcePath); resources.hasMoreElements(); ) {
                    URL resourceURL = resources.nextElement();
                    if (isDebugEnabled()) {
                        debug("Reading binary operator packages from resource [" + resourceURL.toExternalForm() + "] ...");
                    }
                    reader = new BufferedReader(new InputStreamReader(resourceURL.openStream()));
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        line = line.trim();
                        if (line.length() == 0 || line.startsWith("#")) {
                            continue;
                        }
                        List<String> linePackageNames = asCsvList(line);
                        if (linePackageNames != null) {
                            packageNames.addAll(linePackageNames);
                        }
                    }
                }
            }
        } catch (IOException ioEx) {
            throw new TypeConversionException("Unable to load binary operator package names [" + getBinaryOpetratorrPackagesResource() + "] through classloader [" + cl + "]: ", ioEx);
        } finally {
            IoUtil.closeIgnoringErrors(reader);
        }
    }

    protected void loadBinaryOperators(BinaryOperatorRegistry registry, Set<Class<?>> classes) {
        Set<Class<?>> visitedClasses = new HashSet<>();
        for (Class<?> clazz : classes) {
            loadBinaryOperators(visitedClasses, registry, clazz);
        }
    }

    protected void loadBinaryOperators(Set<Class<?>> visitedClasses, BinaryOperatorRegistry registry, Class<?> clazz) {
        if (ANNOTATED_CLASS_FILTER.test(clazz)) {
            loadAnnotatedBinaryOPerators(visitedClasses, registry, clazz);
        }
    }

    protected void loadAnnotatedBinaryOPerators(Set<Class<?>> visitedClasses, BinaryOperatorRegistry registry, Class<?> clazz) {
        if (visitedClasses.contains(clazz)) {
            return;
        }

        getMethodsInClassHierarchy(clazz)
                .filter(ANNOTATED_METHOD_FILTER)
                .forEach(registry::addOperatorMethod);

        visitedClasses.add(clazz);
    }

    public static void main(String... args) {
        new PackageScanBinaryOperatorLoader().load(new ConcurrentBinaryOperatorRegistry());
    }
}

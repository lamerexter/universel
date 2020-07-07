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
package org.orthodox.universel.exec.navigation;

import org.beanplanet.core.io.IoUtil;
import org.beanplanet.core.lang.FilteringPackageClassScanner;
import org.beanplanet.core.lang.PackageResourceScanner;
import org.beanplanet.core.lang.conversion.TypeConversionException;
import org.beanplanet.core.logging.Logger;
import org.orthodox.universel.ast.navigation.ListNodeTest;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.navigation.ReductionNodeTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

import static java.lang.reflect.Modifier.isPublic;
import static java.util.Arrays.asList;
import static org.beanplanet.core.lang.TypeUtil.getMethodsInClassHierarchy;
import static org.beanplanet.core.util.CollectionUtil.nullSafe;
import static org.beanplanet.core.util.StringUtil.asCsvList;
import static org.beanplanet.core.util.StringUtil.asDelimitedString;
import static org.orthodox.universel.cst.Modifiers.isStatic;

public class PackageScanNavigatorLoader implements NavigatorLoader, Logger {
    /**
     * The default top-level resource from which navigators will be loaded.
     */
    public static final String NAVIGATOR_PACKAGES_RESOURCE = "META-INF/services/org/orthodox/universal/navigator-packages.txt";

    /**
     * The top-level resource from which navigators will be loaded.
     */
    protected String navigatorPackagesResource = NAVIGATOR_PACKAGES_RESOURCE;

    public PackageScanNavigatorLoader() {
    }

    public PackageScanNavigatorLoader(final String navigatorPackagesResource) {
        this.navigatorPackagesResource = navigatorPackagesResource;
    }

    protected static final Predicate<Class<?>> ANNOTATED_CLASS_FILTER = new Predicate<Class<?>>() {
        public boolean test(Class<?> clazz) {
            return clazz.isAnnotationPresent(Navigator.class);
        }
    };
    protected static final Predicate<Class<?>> PACKAGE_SCAN_CLASS_FILTER = ANNOTATED_CLASS_FILTER;

    protected PackageResourceScanner<Class<?>> packageScanner = new FilteringPackageClassScanner(PACKAGE_SCAN_CLASS_FILTER);

    private static final Predicate<Method> ANNOTATED_MAPPING_FILTER = method -> {
        Class<?>[] paramTypes = method.getParameterTypes();
        return isPublic(method.getModifiers())
               && isStatic(method.getModifiers())
               && !Modifier.isAbstract(method.getModifiers())
               && method.isAnnotationPresent(MappingNavigator.class)
               && (paramTypes.length == 2)
               && paramTypes[0] == Class.class
               && paramTypes[1] == NavigationStep.class;
    };

    private static final Predicate<Method> ANNOTATED_REDUCTION_FILTER = method -> {
        Class<?>[] paramTypes = method.getParameterTypes();
        return isPublic(method.getModifiers())
               && isStatic(method.getModifiers())
               && !Modifier.isAbstract(method.getModifiers())
               && method.isAnnotationPresent(ReductionNavigator.class)
               && (paramTypes.length == 2)
               && paramTypes[0] == Class.class
               && paramTypes[1] == NavigationStep.class;
    };

    /**
     * Gets the top-level resource from which navigators will be loaded.
     *
     * @return the top-level resource from which navigators will be loaded.
     */
    public String getNavigatorPackagesResource() {
        return navigatorPackagesResource;
    }

    /**
     * Sets the top-level resource from which navigators will be loaded.
     *
     * @param navigatorPackagesResource the top-level resource from which navigators will be loaded.
     */
    public void setNavigatorPackagesResource(String navigatorPackagesResource) {
        this.navigatorPackagesResource = navigatorPackagesResource;
    }

    public void load(NavigatorRegistry registry) {
        // ------------------------------------------------------------------------
        // Add context class loader packages first, if available
        // ------------------------------------------------------------------------
        String[] packageNames = findPackageNames();
        if (packageNames.length > 0) {
            if (isDebugEnabled()) {
                debug("Discovered navigator " + (packageNames.length == 1 ? "package" : "packages") + ": \n" + asDelimitedString(packageNames, ",\n"));
            }

            Set<Class<?>> classes = packageScanner.findResourcesInPackages(packageNames);
            loadNavigators(registry, classes);

            if (isDebugEnabled()) {
                debug("Loaded " + registry.size() + " navigators from " + classes.size() + (classes.size() == 1 ? " class in " : " classes in ") + packageNames.length + (packageNames.length == 1 ? " package " : " packages ") + "into registry");
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
            debug("Searching for annotation-configured navigators [classLoader=" + cl.getClass().getName() + ", resource(s)=" + getNavigatorPackagesResource() + "] ...");
        }

        BufferedReader reader = null;
        try {
            for (String resourcePath : nullSafe(asCsvList(getNavigatorPackagesResource()))) {
                for (Enumeration<URL> resources = cl.getResources(resourcePath); resources.hasMoreElements(); ) {
                    URL resourceURL = resources.nextElement();
                    if (isDebugEnabled()) {
                        debug("Reading navigator packages from resource [" + resourceURL.toExternalForm() + "] ...");
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
            throw new TypeConversionException("Unable to load navigator package names [" + getNavigatorPackagesResource() + "] through classloader [" + cl + "]: ", ioEx);
        } finally {
            IoUtil.closeIgnoringErrors(reader);
        }
    }

    protected void loadNavigators(NavigatorRegistry registry, Set<Class<?>> classes) {
        Set<Class<?>> visitedClasses = new HashSet<>();
        for (Class<?> clazz : classes) {
            loadMappingFilters(visitedClasses, registry, clazz);
        }

        visitedClasses = new HashSet<>();
        for (Class<?> clazz : classes) {
            loadReductionFilters(visitedClasses, registry, clazz);
        }
    }

    protected void loadMappingFilters(Set<Class<?>> visitedClasses, NavigatorRegistry registry, Class<?> clazz) {
        if (visitedClasses.contains(clazz)) {
            return;
        }

        getMethodsInClassHierarchy(clazz)
                .filter(ANNOTATED_MAPPING_FILTER)
                .forEach(m -> addStaticMethodMappingNavigatorToRegistry(registry, m));

        visitedClasses.add(clazz);
    }

    protected void loadReductionFilters(Set<Class<?>> visitedClasses, NavigatorRegistry registry, Class<?> clazz) {
        if (visitedClasses.contains(clazz)) {
            return;
        }

        getMethodsInClassHierarchy(clazz)
            .filter(ANNOTATED_REDUCTION_FILTER)
            .forEach(m -> addStaticMethodReductionNavigatorToRegistry(registry, m));

        visitedClasses.add(clazz);
    }

    private void addStaticMethodReductionNavigatorToRegistry(final NavigatorRegistry registry, final Method method) {
        ReductionNavigator navigatorAnnotation = method.getAnnotation(ReductionNavigator.class);
        registry.addReductionNavigator(determineNavigationSourceType(method.getGenericParameterTypes()[0]),
                                       asList(navigatorAnnotation.axis().length == 0 ? navigatorAnnotation.value() : navigatorAnnotation.axis()),
                                       determineReductionNodeTestType(method.getGenericParameterTypes()[1]),
                                       new StaticMethodNavigatorFunction(method)
        );
    }

    @SuppressWarnings("unchecked")
    private Class<? extends ReductionNodeTest> determineReductionNodeTestType(final Type genericParameterType) {
        if ( genericParameterType instanceof ParameterizedType ) {
            ParameterizedType parameterizedType = (ParameterizedType) genericParameterType;
            if ( parameterizedType.getActualTypeArguments().length == 1
                 && parameterizedType.getActualTypeArguments()[0] instanceof Class) {
//                 && ((Class)parameterizedType.getActualTypeArguments()[0]).isAssignableFrom(ReductionNodeTest.class) ) {
                return (Class)parameterizedType.getActualTypeArguments()[0];
            }
        }

        throw new UnsupportedOperationException();
    }

    private void addStaticMethodMappingNavigatorToRegistry(NavigatorRegistry registry, Method method) {
        MappingNavigator navigatorAnnotation = method.getAnnotation(MappingNavigator.class);
        registry.addMappingNavigator(determineNavigationSourceType(method.getGenericParameterTypes()[0]),
                                     asList(navigatorAnnotation.axis().length == 0 ? navigatorAnnotation.value() : navigatorAnnotation.axis()),
                                     asList(navigatorAnnotation.name()),
                                     new StaticMethodNavigatorFunction(method)
        );
    }

    private Class<?> determineNavigationSourceType(Type genericParameterType) {
        if ( genericParameterType instanceof ParameterizedType ) {
            ParameterizedType parameterizedType = (ParameterizedType) genericParameterType;
            if ( parameterizedType.getActualTypeArguments().length == 1) {
                if (parameterizedType.getActualTypeArguments()[0] instanceof WildcardType) {
                    WildcardType wildcardType = (WildcardType) parameterizedType.getActualTypeArguments()[0];
                    if ( wildcardType.getUpperBounds().length == 1 && wildcardType.getUpperBounds()[0] instanceof Class) {
                        return (Class<?>)wildcardType.getUpperBounds()[0];
                    }
                }
            }
        }

        throw new UnsupportedOperationException();
    }

    public static void main(String... args) {
        new PackageScanNavigatorLoader().load(new ConcurrentNavigatorRegistry());
    }
}

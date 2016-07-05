package de.jodamob.kotlin.testrunner

import org.junit.runners.model.InitializationError

fun configureClassOpeningClassLoader(klass: Class<*>): Class<*>? {
    val classFilter = getProcessedClassesFromAnnotation(klass) ?: getCurrentPackageClassFilter(klass)
    val classOpeningClassLoader = NoMoreFinalsClassLoader(classFilter, klass)
    return classOpeningClassLoader.loadClass(klass.name)
}

private fun getCurrentPackageClassFilter(klass: Class<*>): ClassFilter {
    println("WARNING: Class '${klass.name}' doesn't have either OpenedPackages nor OpenedClasses annotation. " +
            "Failing back to current package.")
    return ClassFilter(listOf(klass.packageName), emptyList())
}

@Throws(InitializationError::class)
private fun getProcessedClassesFromAnnotation(klass: Class<*>): ClassFilter? {
    val openedPackages: List<String> = klass
            .getAnnotation<OpenedPackages>(OpenedPackages::class.java)
            ?.value
            .orEmpty()
            .toList()
    val openedClasses = klass
            .getAnnotation<OpenedClasses>(OpenedClasses::class.java)
            ?.value
            .orEmpty()
            .toList()
    return if (openedPackages.isNotEmpty() || openedClasses.isNotEmpty()) ClassFilter(openedPackages, openedClasses) else null
}
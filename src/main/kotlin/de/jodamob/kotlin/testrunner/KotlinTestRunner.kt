package de.jodamob.kotlin.testrunner

import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.InitializationError

class KotlinTestRunner(klass: Class<*>) : BlockJUnit4ClassRunner(load(klass)) {

    companion object {

        fun load(klass: Class<*>): Class<*>? {
            val classOpeningClassLoader = NoMoreFinalsClassLoader(getAnnotatedClasses(klass), klass)
            return classOpeningClassLoader.loadClass(klass.name)
        }

        @Throws(InitializationError::class)
        private fun getAnnotatedClasses(klass: Class<*>): ClassFilter {
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

            if (openedPackages.isNotEmpty() || openedClasses.isNotEmpty()) {
                return ClassFilter(openedPackages, openedClasses)
            } else {
                throw InitializationError("class '${klass.name}' must have a either OpenedPackages or OpenedClasses annotation")
            }
        }
    }
}


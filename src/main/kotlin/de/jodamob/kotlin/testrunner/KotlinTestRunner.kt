package de.jodamob.kotlin.testrunner

import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.InitializationError

class KotlinTestRunner(klass: Class<*>) : BlockJUnit4ClassRunner(load(klass)) {

    companion object {

        fun load(klass: Class<*>): Class<*>? {
            val classOpeningClassLoader = NoMoreFinalsClassLoader(*getAnnotatedClasses(klass))
            return classOpeningClassLoader.loadClass(klass.name)
        }

        @Throws(InitializationError::class)
        private fun getAnnotatedClasses(klass: Class<*>): Array<out String> {
            return (klass.getAnnotation<OpenedClasses>(OpenedClasses::class.java)
                    ?: throw InitializationError(String.format("class '%s' must have a OpenedClasses annotation", klass.name))).value
        }
    }
}


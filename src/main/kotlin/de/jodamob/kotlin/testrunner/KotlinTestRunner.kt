package de.jodamob.kotlin.testrunner

import org.junit.runners.BlockJUnit4ClassRunner

class KotlinTestRunner(klass: Class<*>?) : BlockJUnit4ClassRunner(load(klass)) {

    companion object {
        private  val myClassLoader = MyClassLoader()
        fun load(clazz: Class<*>?): Class<*>? {
            val loadClass = myClassLoader.loadClass(clazz!!.name)
            return loadClass
        }
    }
}


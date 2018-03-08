package de.jodamob.kotlin.testrunner.sample

open class SuperClassSample {

    fun superFinalMethod(): Unit = throw IllegalAccessError("you should not see this")
}

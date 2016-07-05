package de.jodamob.kotlin.testrunner.sample

class FinalClassSample : TestedClass {

    override fun finalMethod(): Unit = throw IllegalAccessError("you should not see this")
}
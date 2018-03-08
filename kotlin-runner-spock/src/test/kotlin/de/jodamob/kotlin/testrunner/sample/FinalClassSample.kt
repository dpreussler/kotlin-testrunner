package de.jodamob.kotlin.testrunner.sample

class FinalClassSample : SuperClassSample(), TestedClass {

    override fun finalMethod(): Unit = throw IllegalAccessError("you should not see this")
}

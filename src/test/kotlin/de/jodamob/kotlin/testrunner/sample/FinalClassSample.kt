package de.jodamob.kotlin.testrunner.sample

class FinalClassSample : TestedClass {

    override fun finalMethod() {
        throw IllegalAccessError("you should not see this")
    }
}
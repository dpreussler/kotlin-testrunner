package de.jodamob.kotlin.testrunner.sample

class FinalClassSample {

    fun finalMethod() {
        throw IllegalAccessError("you should not see this")
    }
}
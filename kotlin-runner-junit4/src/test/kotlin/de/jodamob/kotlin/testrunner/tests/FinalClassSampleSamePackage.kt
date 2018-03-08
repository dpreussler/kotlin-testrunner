package de.jodamob.kotlin.testrunner.tests

import de.jodamob.kotlin.testrunner.sample.TestedClass

class FinalClassSampleSamePackage : TestedClass {

    override fun finalMethod() {
        throw IllegalAccessError("you should not see this")
    }

    override fun superFinalMethod() {
        throw IllegalAccessError("you should not see this")
    }
}

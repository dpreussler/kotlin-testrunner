package de.jodamob.kotlin.testrunner.nonopen

import de.jodamob.kotlin.testrunner.sample.TestedClass

class FinalNonOpenClassSample : TestedClass {

    override fun finalMethod() {
        throw IllegalAccessError("you should not see this")
    }
}
package de.jodamob.kotlin.testrunner.nonopen

import de.jodamob.kotlin.testrunner.sample.SuperClassSample
import de.jodamob.kotlin.testrunner.sample.TestedClass

class FinalNonOpenClassSample : SuperClassSample(), TestedClass {

    override fun finalMethod() {
        throw IllegalAccessError("you should not see this")
    }
}

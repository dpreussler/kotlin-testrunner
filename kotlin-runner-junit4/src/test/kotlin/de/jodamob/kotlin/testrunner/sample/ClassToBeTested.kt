package de.jodamob.kotlin.testrunner.sample

class ClassToBeTested(val classUnderTest: TestedClass) {

    fun callMe() {
        classUnderTest.finalMethod()
    }
}
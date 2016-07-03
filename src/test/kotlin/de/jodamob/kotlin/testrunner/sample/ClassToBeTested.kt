package de.jodamob.kotlin.testrunner.sample

class ClassToBeTested(val finalClassSample: FinalClassSample) {

    fun callMe() {
        finalClassSample.finalMethod()
    }
}
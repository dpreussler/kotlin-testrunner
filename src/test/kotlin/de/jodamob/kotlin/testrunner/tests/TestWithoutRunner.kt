package de.jodamob.kotlin.testrunner.tests

import de.jodamob.kotlin.testrunner.sample.ClassToBeTested
import de.jodamob.kotlin.testrunner.sample.FinalClassSample
import org.junit.Test
import org.mockito.Mockito
import org.mockito.exceptions.base.MockitoException

class TestWithoutRunner {

    @Test(expected = IllegalAccessError::class)
    fun should_fail_without_mock() {
        val finalClassSample = FinalClassSample()
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }

    @Test(expected = MockitoException::class)
    fun should_fail_trying_with_mock() {
        val finalClassSample = Mockito.mock(FinalClassSample::class.java)
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }
}
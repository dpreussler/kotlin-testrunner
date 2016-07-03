package de.jodamob.kotlin.testrunner.sample

import de.jodamob.kotlin.testrunner.KotlinTestRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.exceptions.base.MockitoException

@RunWith(KotlinTestRunner::class)
class TestWithRunner {


    @Test(expected = IllegalAccessError::class)
    fun should_fail_without_mock() {
        val finalClassSample = FinalClassSample()
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }

    @Test
    fun should_work() {
        val finalClassSample = Mockito.mock(FinalClassSample::class.java)
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }
}
package de.jodamob.kotlin.testrunner.sample

import de.jodamob.kotlin.testrunner.KotlinTestRunner
import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.nonopen.FinalNonOpenClassSample
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.exceptions.base.MockitoException

@RunWith(KotlinTestRunner::class)
@OpenedClasses("de.jodamob.kotlin.testrunner.sample")
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

    @Test(expected = MockitoException::class)
    fun should_open_classes_within_filter_only() {
        val finalClassSample = Mockito.mock(FinalNonOpenClassSample::class.java)
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }
}
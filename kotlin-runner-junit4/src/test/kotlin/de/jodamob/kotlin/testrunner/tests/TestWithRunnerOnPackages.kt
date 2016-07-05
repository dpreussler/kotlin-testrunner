package de.jodamob.kotlin.testrunner.tests

import de.jodamob.kotlin.testrunner.KotlinTestRunner
import de.jodamob.kotlin.testrunner.OpenedPackages
import de.jodamob.kotlin.testrunner.nonopen.FinalNonOpenClassSample
import de.jodamob.kotlin.testrunner.sample.ClassToBeTested
import de.jodamob.kotlin.testrunner.sample.FinalClassSample
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.exceptions.base.MockitoException

@RunWith(KotlinTestRunner::class)
@OpenedPackages("de.jodamob.kotlin.testrunner.sample")
class TestWithRunnerOnPackages {

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
        val finalClassSample = org.mockito.Mockito.mock(FinalNonOpenClassSample::class.java)
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }
}
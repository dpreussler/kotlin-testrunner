package de.jodamob.kotlin.testrunner.tests

import de.jodamob.kotlin.testrunner.KotlinTestRunner
import de.jodamob.kotlin.testrunner.nonopen.FinalNonOpenClassSample
import de.jodamob.kotlin.testrunner.sample.ClassToBeTested
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.exceptions.base.MockitoException

@RunWith(KotlinTestRunner::class)
class TestWithRunnerWithoutConfiguration {

    @Test
    fun should_open_class_in_same_package() {
        val finalClassSample = org.mockito.Mockito.mock(FinalClassSampleSamePackage::class.java)
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }

    @Test(expected = org.mockito.exceptions.base.MockitoException::class)
    fun should_not_open_classes_in_different_package() {
        val finalClassSample = org.mockito.Mockito.mock(FinalNonOpenClassSample::class.java)
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }
}
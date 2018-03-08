package de.jodamob.kotlin.testrunner.tests

import de.jodamob.kotlin.testrunner.KotlinTestRunner
import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.nonopen.FinalNonOpenClassSample
import de.jodamob.kotlin.testrunner.sample.ClassToBeTested
import de.jodamob.kotlin.testrunner.sample.FinalClassSample
import de.jodamob.kotlin.testrunner.sample.FinalClassTwoSample
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(KotlinTestRunner::class)
@OpenedClasses(FinalClassSample::class, FinalClassTwoSample::class)
class TestWithRunnerOnClasses {

    @Test(expected = IllegalAccessError::class)
    fun should_fail_without_mock() {
        val finalClassSample = FinalClassSample()
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }

    @Test(expected = IllegalAccessError::class)
    fun should_fail_for_super_class_methods_without_mock() {
        val finalClassSample = FinalClassSample()
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMeSuper()
    }

    @Test
    fun should_work() {
        val finalClassSample = org.mockito.Mockito.mock(FinalClassSample::class.java)
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }

    @Test
    fun should_work_tpp() {
        val finalClassSample = org.mockito.Mockito.mock(FinalClassTwoSample::class.java)
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }

    @Test
    fun should_work_for_super_class_methods() {
        val finalClassSample = org.mockito.Mockito.mock(FinalClassSample::class.java)
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMeSuper()
    }

    @Test(expected = org.mockito.exceptions.base.MockitoException::class)
    fun should_open_classes_within_filter_only() {
        val finalClassSample = org.mockito.Mockito.mock(FinalNonOpenClassSample::class.java)
        val classToBeTested = ClassToBeTested(finalClassSample)
        classToBeTested.callMe()
    }
}

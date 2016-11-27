package de.jodamob.kotlin.testrunner.tests

import de.jodamob.kotlin.testrunner.KotlinTestRunner
import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.sample.OpenClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(KotlinTestRunner::class)
@OpenedClasses(OpenClass::class)
class TestWithRunnerOnOpenClass {

    @Test
    fun should_open_non_open_method(){
        val mockOpenClass = org.mockito.Mockito.mock(OpenClass::class.java)
        org.mockito.Mockito.`when`(mockOpenClass.nonOpenMethod()).thenReturn("Non Open Mock Response")

        val nonOpenResult = mockOpenClass.nonOpenMethod()

        assert("Non Open Mock Response".equals(nonOpenResult))
    }

    @Test
    fun should_not_affect_open_method(){
        val mockOpenClass = org.mockito.Mockito.mock(OpenClass::class.java)
        org.mockito.Mockito.`when`(mockOpenClass.openMethod()).thenReturn("Open Mock Response")

        val openResult = mockOpenClass.openMethod()

        assert("Open Mock Response".equals(openResult))
    }

}
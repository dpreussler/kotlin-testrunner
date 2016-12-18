package de.jodamob.kotlin.testrunner.tests

import de.jodamob.kotlin.testrunner.sample.OpenClass
import org.junit.Test

class TestWithoutRunnerOnOpenClass {

    @Test(expected = NotImplementedError::class)
    fun should_open_non_open_method(){
        val mockOpenClass = org.mockito.Mockito.mock(OpenClass::class.java)
        org.mockito.Mockito.`when`(mockOpenClass.nonOpenMethod()).thenReturn("Non Open Mock Response")
    }

    @Test
    fun should_not_affect_open_method(){
        val mockOpenClass = org.mockito.Mockito.mock(OpenClass::class.java)
        org.mockito.Mockito.`when`(mockOpenClass.openMethod()).thenReturn("Open Mock Response")

        val openResult = mockOpenClass.openMethod()

        assert("Open Mock Response".equals(openResult))
    }

}
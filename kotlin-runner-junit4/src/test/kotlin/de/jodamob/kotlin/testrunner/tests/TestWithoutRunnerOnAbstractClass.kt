package de.jodamob.kotlin.testrunner.tests

import de.jodamob.kotlin.testrunner.sample.AbstractClass
import org.junit.Test

class TestWithoutRunnerOnAbstractClass {

    @Test(expected = NotImplementedError::class)
    fun should_open_non_open_method(){
        val mockAbstractClass = org.mockito.Mockito.mock(AbstractClass::class.java)
        org.mockito.Mockito.`when`(mockAbstractClass.nonOpenMethod()).thenReturn("Non Open Mock Response")
    }

    @Test
    fun should_not_affect_open_method(){
        val mockAbstractClass = org.mockito.Mockito.mock(AbstractClass::class.java)
        org.mockito.Mockito.`when`(mockAbstractClass.openMethod()).thenReturn("Open Mock Response")

        val openResult = mockAbstractClass.openMethod()

        assert("Open Mock Response".equals(openResult))
    }

}
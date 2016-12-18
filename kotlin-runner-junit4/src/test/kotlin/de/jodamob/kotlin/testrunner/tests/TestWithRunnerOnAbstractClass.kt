package de.jodamob.kotlin.testrunner.tests

import de.jodamob.kotlin.testrunner.KotlinTestRunner
import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.sample.AbstractClass
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(KotlinTestRunner::class)
@OpenedClasses(AbstractClass::class)
class TestWithRunnerOnAbstractClass {

    @Test
    fun should_open_non_open_method(){
        val mockAbstractClass = org.mockito.Mockito.mock(AbstractClass::class.java)
        org.mockito.Mockito.`when`(mockAbstractClass.nonOpenMethod()).thenReturn("Non Open Mock Response")

        val nonOpenResult = mockAbstractClass.nonOpenMethod()

        assert("Non Open Mock Response".equals(nonOpenResult))
    }

    @Test
    fun should_not_affect_open_method(){
        val mockAbstractClass = org.mockito.Mockito.mock(AbstractClass::class.java)
        org.mockito.Mockito.`when`(mockAbstractClass.openMethod()).thenReturn("Open Mock Response")

        val openResult = mockAbstractClass.openMethod()

        assert("Open Mock Response".equals(openResult))
    }

}
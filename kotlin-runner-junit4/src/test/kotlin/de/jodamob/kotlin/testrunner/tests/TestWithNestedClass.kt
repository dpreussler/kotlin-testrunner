package de.jodamob.kotlin.testrunner.tests

import de.jodamob.kotlin.testrunner.KotlinTestRunner
import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.OuterJava
import de.jodamob.kotlin.testrunner.sample.Outer
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(KotlinTestRunner::class)
@OpenedClasses(Outer::class, Outer.Nested::class, OuterJava::class, OuterJava.NestedJava::class)
class TestWithNestedClass {

    @Test
    fun `outer mock works`() {
        Mockito.mock(Outer::class.java)
    }

    @Test fun `nested mock doesnt work`() {
        Mockito.mock(Outer.Nested::class.java)
    }

    @Test
    fun `outer java mock works`() {
        Mockito.mock(OuterJava::class.java)
    }

    @Test fun `nested java mock doesnt work`() {
        Mockito.mock(OuterJava.NestedJava::class.java)
    }
}
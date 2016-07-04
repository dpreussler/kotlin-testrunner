package de.jodamob.kotlin.testrunner

import java.lang.annotation.Inherited
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Inherited annotation class OpenedClasses(vararg val value: KClass<*>)
package de.jodamob.kotlin.testrunner

import java.lang.annotation.Inherited

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Inherited annotation class OpenedClasses(vararg val value: String)
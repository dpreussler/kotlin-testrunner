package de.jodamob.kotlin.testrunner

import org.spockframework.runtime.Sputnik

/**
 * Test runner to be used with Spock based tests
 */
class SpotlinTestRunner(klass: Class<*>) : Sputnik(configureClassOpeningClassLoader(klass))
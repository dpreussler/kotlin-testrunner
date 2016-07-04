package de.jodamob.kotlin.testrunner

import org.junit.runners.BlockJUnit4ClassRunner

class KotlinTestRunner(klass: Class<*>) : BlockJUnit4ClassRunner(configureClassOpeningClassLoader(klass))


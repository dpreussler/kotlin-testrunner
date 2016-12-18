package de.jodamob.kotlin.testrunner.sample

open class OpenClass {
    fun nonOpenMethod(): String{
        throw NotImplementedError("OpenClass#NonOpen NotImplementedError")
    }

    open fun openMethod(): String{
        throw NotImplementedError("OpenClass#openMethod NotImplementedError")
    }
}
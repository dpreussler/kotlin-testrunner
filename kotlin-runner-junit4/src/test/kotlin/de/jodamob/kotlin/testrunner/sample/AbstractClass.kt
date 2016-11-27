package de.jodamob.kotlin.testrunner.sample

abstract class AbstractClass{
    fun nonOpenMethod(): String{
        throw NotImplementedError("AbstractClass#nonOpenMethod NotImplementedError")
    }

    open fun openMethod(): String{
        throw NotImplementedError("AbstractClass#openMethod NotImplementedError")
    }
}
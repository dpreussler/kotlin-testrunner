package de.jodamob.kotlin.testrunner

import kotlin.reflect.KClass

internal val KClass<*>.packageName: String
    get() = qualifiedName!!.removeSuffix(".$simpleName")

internal val Class<*>.packageName: String
    get() = `package`.name

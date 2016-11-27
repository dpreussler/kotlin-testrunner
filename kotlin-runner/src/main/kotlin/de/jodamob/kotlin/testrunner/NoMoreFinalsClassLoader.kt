package de.jodamob.kotlin.testrunner

import javassist.ClassPool
import javassist.CtClass
import javassist.LoaderClassPath
import javassist.Modifier
import kotlin.reflect.KClass
import java.lang.reflect.Modifier as ReflectModifier

internal class NoMoreFinalsClassLoader(val classFilter: ClassFilter, val rootClass: Class<*>) : ClassLoader() {

    private val pool = ClassPool(false)
    private val processedPackages: List<String>

    init {
        pool.appendClassPath(LoaderClassPath(this))
        pool.appendSystemPath()
        processedPackages = (classFilter.packages + classFilter.classes.map { it.packageName }).distinct()
    }

    @Throws(ClassNotFoundException::class)
    override fun loadClass(className: String): Class<*> {
        return if (isRootClass(className) || className.isInProcessedPackage(processedPackages)) {
            process<Class<*>>(className)
        } else {
            super.loadClass(className)
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(Exception::class)
    fun <T> process(className: String): Class<T> {
        val defaultClass = pool.get(className)
        return if (isIncluded(className) && !isPublicOrFinalOrIrrelevant(className, defaultClass)) {
            removeFinal(defaultClass) as Class<T>
        } else {
            defaultClass.toClass(this) as Class<T>
        }
    }

    private fun isPublicOrFinalOrIrrelevant(className: String, defaultClass: CtClass): Boolean {
        return className.endsWith("Test") || className.endsWith("TestRunner") ||
                ReflectModifier.isStatic(defaultClass.modifiers) ||
                !ReflectModifier.isPublic(defaultClass.modifiers) ||
                !ReflectModifier.isFinal(defaultClass.modifiers)
    }

    fun removeFinal(clazz: CtClass): Class<*>? {
        removeFinalOnClass(clazz)
        removeFinalOnMethods(clazz)
        clazz.stopPruning(true)
        return clazz.toClass(this)
    }

    private fun removeFinalOnMethods(clazz: CtClass) {
        clazz.declaredMethods.forEach {
            if (ReflectModifier.isPublic(it.modifiers) && ReflectModifier.isFinal(it.modifiers)) {
                it.modifiers = Modifier.clear(it.modifiers, java.lang.reflect.Modifier.FINAL)
            }
        }
    }

    private fun removeFinalOnClass(clazz: CtClass) {
        val modifiers = clazz.modifiers
        if (ReflectModifier.isFinal(modifiers)) {
            clazz.modifiers = Modifier.clear(modifiers, java.lang.reflect.Modifier.FINAL)
        }
    }

    private fun String.isInProcessedPackage(includes: List<String>): Boolean {
        includes.forEach {
            if (this.startsWith(it, true)) return true
        }

        return false
    }

    private fun isIncluded(className: String)
            = className.isInProcessedPackage(classFilter.packages) || classFilter.classes.any { it.jvmName == className }

    private fun isRootClass(className: String) = className == rootClass.canonicalName
}

internal data class ClassFilter(val packages: List<String>, val classes: List<KClass<*>>)
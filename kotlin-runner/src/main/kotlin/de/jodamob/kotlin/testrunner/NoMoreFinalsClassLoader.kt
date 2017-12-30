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
    private val loadedClasses = mutableMapOf<String, Class<*>>()

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
        try {
            val defaultClass = pool.get(className)
            return loadedClasses.getOrPut(className, {
                if (isIncluded(className) && !isStaticOrNotPublic(className, defaultClass)) {
                    System.err.println("removing" + className)
                    removeFinal(defaultClass) as Class<T>
                } else {
                    System.err.println("loading" + className)
                    defaultClass.toClass(this)
                }
            }) as Class<T>
        } catch (notFound: javassist.NotFoundException) {
            throw ClassNotFoundException(notFound.message, notFound)
        }
    }

    private fun isStaticOrNotPublic(className: String, defaultClass: CtClass): Boolean {
        return className.endsWith("Test") || className.endsWith("TestRunner") ||
                ReflectModifier.isStatic(defaultClass.modifiers) ||
                !ReflectModifier.isPublic(defaultClass.modifiers)
    }

    fun removeFinal(clazz: CtClass): Class<*>? {
        return clazz.apply {
            removeFinalOnClass()
            removeFinalOnMethods()
            stopPruning()
        }.toClass(this)
    }

    private fun CtClass.stopPruning() = stopPruning(true)

    private fun CtClass.removeFinalOnMethods() {
        declaredMethods.forEach {
            if (ReflectModifier.isPublic(it.modifiers) && ReflectModifier.isFinal(it.modifiers)) {
                it.modifiers = Modifier.clear(it.modifiers, java.lang.reflect.Modifier.FINAL)
            }
        }
        nestedClasses.forEach { it.removeFinalOnMethods() }
    }

    private fun CtClass.removeFinalOnClass() {
        if (ReflectModifier.isFinal(modifiers)) {
            modifiers = Modifier.clear(modifiers, java.lang.reflect.Modifier.FINAL)
        }
        nestedClasses.forEach {
            it.removeFinalOnClass()
        }
    }

    private fun String.isInProcessedPackage(includes: List<String>): Boolean {
        includes.forEach {
            if (this.startsWith(it, true)) return true
        }
        return false
    }

    private fun isIncluded(className: String)
            = className.isInProcessedPackage(classFilter.packages) ||
              classFilter.classes.any {
                  className == it.qualifiedName
              }

    private fun isRootClass(className: String) = className == rootClass.canonicalName
}

internal data class ClassFilter(val packages: List<String>, val classes: List<KClass<*>>)
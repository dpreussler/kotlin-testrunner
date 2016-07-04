package de.jodamob.kotlin.testrunner

import javassist.ClassPool
import javassist.CtClass
import javassist.LoaderClassPath
import javassist.Modifier
import java.lang.reflect.Modifier as ReflectModifier

class NoMoreFinalsClassLoader(vararg openedClasses: String) : ClassLoader() {

    private val pool = ClassPool(false)
    private val openedClasses by lazy { openedClasses.toList() }

    init {
        pool.appendClassPath(LoaderClassPath(this))
        pool.appendSystemPath()
    }

    @Throws(ClassNotFoundException::class)
    override fun loadClass(name: String): Class<*> {
        return if (name.startsWithAny(openedClasses)) {
            process<Class<*>>(name, this)
        } else {
            super.loadClass(name)
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(Exception::class)
    fun <T> process(className: String, classLoader: NoMoreFinalsClassLoader): Class<T> {
        val defaultClass = pool.get(className)
        return if (isPublicOrFinalOrIrrelevant(className, defaultClass)) {
            defaultClass.toClass(classLoader) as Class<T>
        } else {
            removeFinal(defaultClass, classLoader) as Class<T>
        }
    }

    private fun isPublicOrFinalOrIrrelevant(className: String, defaultClass: CtClass): Boolean {
        return className.endsWith("Test") || className.endsWith("TestRunner") ||
                ReflectModifier.isStatic(defaultClass.modifiers) ||
                !ReflectModifier.isPublic(defaultClass.modifiers) ||
                !ReflectModifier.isFinal(defaultClass.modifiers)
    }

    fun removeFinal(clazz: CtClass, classLoader: NoMoreFinalsClassLoader): Class<*>? {
        removeFinalOnClass(clazz)
        removeFinalOnMethods(clazz)
        clazz.stopPruning(true)
        return clazz.toClass(classLoader)
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

    private fun String.startsWithAny(includes: List<String>): Boolean {
        includes.forEach {
            if (this.startsWith(it, true)) return true
        }

        return false
    }
}

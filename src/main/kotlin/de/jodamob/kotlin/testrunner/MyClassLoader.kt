package de.jodamob.kotlin.testrunner

import javassist.ClassPool
import javassist.CtClass
import javassist.LoaderClassPath
import javassist.Modifier
import java.lang.reflect.Modifier as ReflectModifier

class MyClassLoader : ClassLoader {

    val filter = arrayListOf("java.","javax.", "com.google.", "android.", "org.junit.", "org.mockito", "junit.", "kotlin.")
    val pool = ClassPool(false)
    constructor() {
        pool.appendClassPath(LoaderClassPath(this))
        pool.appendSystemPath()
    }

    @Throws(ClassNotFoundException::class)
    override fun loadClass(name: String): Class<*> {
        filter.forEach { if (name.startsWith(it)) return super.loadClass(name) }
        return process<Class<*>>(name, this)
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(Exception::class)
    fun <T> process(className: String, classLoader: MyClassLoader): Class<T> {
        println("Processing class: " + className)
        val defaultClass = pool.get(className)
        if (isPublicOrFinalOrIrrelvant(className, defaultClass)) {
            return defaultClass.toClass(classLoader) as Class<T>
        }
        return removeFinal(defaultClass, classLoader) as Class<T>;
    }

    private fun isPublicOrFinalOrIrrelvant(className: String, defaultClass: CtClass): Boolean {
        return className.endsWith("Test") || className.endsWith("TestRunner") ||
                ReflectModifier.isStatic(defaultClass.modifiers) ||
                !ReflectModifier.isPublic(defaultClass.modifiers) ||
                !ReflectModifier.isFinal(defaultClass.modifiers)
    }

    fun removeFinal(clazz: CtClass, classLoader: MyClassLoader): Class<*>? {
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
}

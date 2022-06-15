package com.example.kotlinlib

import java.io.Serializable

/**
 * 懒汉式单例
 */
class KLazilySingleton private constructor() : Serializable {

    fun doSomething() {
        println("do some thing")
    }

    companion object {
        private var mInstance: KLazilySingleton? = null
            get() {
                return field ?: KLazilySingleton()
            }

        @JvmStatic
        @Synchronized // 添加synchronized同步锁
        fun getInstance(): KLazilySingleton {
            return requireNotNull(mInstance)
        }
    }

    // 防止单例对象在反序列化时重新生成对象
    private fun readResolve(): Any {
        return getInstance()
    }
}

fun main() {
    KLazilySingleton.getInstance().doSomething()
}
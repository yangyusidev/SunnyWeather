package com.example.kotlinlib

// 作为中间类型，实现链式链接
sealed class BooleanExt<out T>
object Otherwise : BooleanExt<Nothing>()
class TransferData<T>(val data: T) : BooleanExt<T>()

inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> = when { // T处于函数返回值位置
    // 由于返回值是BooleanExt，所以此处也需要返回一个BooleanExt对象或其子类对象，
    // 故暂且定义TransferData object继承BooleanExt
    this -> {
        TransferData(block.invoke())
    }
    // 此处为else，那么需要链接起来，所以需要返回一个BooleanExt对象或其子类对象，
    // 故定义Otherwise object继承BooleanExt
    else -> {
        Otherwise
    }
}

// 为了链接起otherwise方法操作所以需要写一个BooleanExt类的扩展
inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T = when (this) {
    // 判断此时子类，如果是Otherwise子类执行block
    is Otherwise -> block()
    is TransferData ->
        this.data
}

fun main() {
    val numberList: List<Int> = listOf(1, 2, 3)
    // 使用定义好的扩展
    (numberList.size == 3).yes {
        println("true")
    }.otherwise {
        println("false")
    }
}


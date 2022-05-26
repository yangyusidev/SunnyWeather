package com.example.kotlinlib

class Generics {

    interface Producer<out T> {
        val something: List<T>
        fun product(): List<Map<String, T>>
    }

}
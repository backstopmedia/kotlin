package com.backstopmedia.kotlin.chapter7

import org.junit.Test

class DecomposeTest {
    @Test fun decomposeClass() {
        class Person(val name: String, val age: Int) {
            operator fun component1(): String = name
            operator fun component2(): Int = age
        }
        val (name, age) = Person("Christina Rudloff", 29)
    }

    @Test fun decomposeDataClass() {
        data class Person(val name: String, val age: Int)
        val (name, age) = Person("Troy Mott", 31)
    }

    @Test fun decomposeArray() {
        val (a, b, c) = arrayOf(1, 2, 3)
    }

    @Test fun decomposeList() {
        val (a, b, c) = listOf(1, 2, 3)
    }

}


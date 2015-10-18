package com.backstopmedia.kotlin.chapter7

import org.junit.Test

@Suppress("UNUSED_VARIABLE")
class SequenceTest {

    @Test fun createSequenceOfArray() {
        val seq = arrayOf(1, 2, 3).asSequence()
    }

    @Test fun createSequenceOfList() {
        val seq = listOf(1, 2, 3).asSequence()
    }

    @Test fun createSequenceOfValues() {
        val seq = sequenceOf(1, 2, 3)
    }

    @Test fun createSequenceOfProgression() {
        val seq = sequenceOf(1..10)
        seq.printElements()
    }

    @Test fun createSequenceByFunction() {
        var i = 0
        val seq = sequence { ++i }
    }

    @Test fun createSequenceBy() {
        var i = 0
        val seq = sequence(i) { ++i + it }
    }

    @Test fun map() {
        val seq = sequenceOf(1, 2, 3).map { it * 2 }
        seq.printElements()
    }

    @Test fun filter() {
        val seq = sequenceOf(1, 2, 3, 4, 5, 6).filter { it and 1 == 0 }
        seq.printElements()
    }

    @Test fun distinct() {
        val seq = sequenceOf(1, 2, 1).distinct()
        seq.printElements()
    }
}


fun <T> Sequence<T>.printElements() {
    val sb = StringBuilder()
    sb.append("[")
    forEach {
        if (sb.length() > 1) sb.append(", ")
        sb.append(it)
    }
    sb.append("]")
    println(sb)
}
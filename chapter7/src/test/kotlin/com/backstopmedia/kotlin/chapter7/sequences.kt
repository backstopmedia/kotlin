package com.backstopmedia.kotlin.chapter7

import org.junit.Test

class SequenceTest {

    @Test fun map() {
        val seq = sequenceOf(1, 2, 3).map { it * 2 }
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
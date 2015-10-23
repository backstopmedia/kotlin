package com.backstopmedia.kotlin.chapter6

import org.junit.Test

class Lists {

    /**
     * [String] is magically converted to [java.lang.String] when needed.
     */
    @Test fun kotlinStrings() {
        val logMessage: kotlin.String = "Something has occurred."
        System.out.println(logMessage) // This method expects java.lang.String
    }

    /**
     * [List] is immutable and has no add or put methods.
     * list[1] resolves to [List.get]
     */
    @Test fun lists() {
        val list = listOf("do", "re", "mi") // List<String>
        //   list.add("fa") // Compilation Failure. No such method.
        val re = list[1]
        println("list[1] is $re")
    }

    /**
     * If you want a mutable list, [ArrayList] is your best bet.
     * list[1] resolves to [MutableList.set]
     */
    @Test fun arrayLists() {
        val foobar = arrayListOf("foo", "bar")
        foobar[1] = "baz"
        println("foobar is actually $foobar") // foo baz
    }

    /**
     * You can also use operators for removal and addition.
     * This will create new immutable lists, rather than mutating the original.
     */
    @Test fun operators() {
        val foobar = listOf("foo", "bar")
        val football = (foobar - "bar" + "tball").joinToString("")
        println("We can also turn foobar into $football")
        println("But the original will always be $foobar!")
    }

    /**
     * You can easily convert one list into another using [List.map]
     * Sorting is also easily accomplished.
     */
    @Test fun fmap() {
        val animals = listOf("walrus", "eel", "giraffe")
        val lengths = animals.map { it.length }
        println("lengths are $lengths")
    }

}
package com.v.minesweeper

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun lambda_test() {
        val l = a("1", {
            return@a 2
        })
        println("l-->$l")
    }

    @Test
    fun forLoopTest() {
        for (i in 0..10) {
            println(i)
        }
    }

    @Test
    fun randomTest() {
        val r = Random()
        for (i in r.ints(10, 0, 100).toArray()) {
            print("$i    ")
        }
    }

    @Test
    fun arrayTest() {
        var array = emptyList<Int>()
    }

    private inline fun a(s: String, f: (String) -> Int): Int = f(s)

}

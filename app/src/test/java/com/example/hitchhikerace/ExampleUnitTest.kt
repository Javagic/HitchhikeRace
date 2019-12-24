package com.example.hitchhikerace

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val a = true
        val b = false
        val c = false
        val res1 = a || b && c
        val res2 = (a || b) && c
        val res3 = a || (b && c)
        assertEquals(res1, res2)
        assertEquals(res3, res1)

    }
}

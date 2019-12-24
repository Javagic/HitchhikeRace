package com.example.hitchhikerace

import android.location.Location
import org.junit.Test

class UserLocationTest {

    @Test
    fun testLocation() {
        val a = Location("").apply {
            latitude = 59.94008278661738
            longitude = 30.266595609472997
        }.distanceTo(Location("").apply {
            latitude = 59.940017
            longitude = 30.267029
        })

    }
}

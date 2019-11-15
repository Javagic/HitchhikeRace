package com.example.hitchhikerace.database

import androidx.room.Delete
import androidx.room.Insert

interface BaseDao<T> {

    @Insert
    fun insert(obj: T): Long

    @Insert
    fun insert(obj: List<T>): Array<Long>

    @Delete
    fun delete(obj: T)

    @Delete
    fun delete(obj: List<T>)

}
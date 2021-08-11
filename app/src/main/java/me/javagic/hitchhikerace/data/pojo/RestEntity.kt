package me.javagic.hitchhikerace.data.pojo

data class RestEntity(val hour: Int = 0, val minute: Int = 0, val partitions: Int = 0) {

    override fun toString(): String {
        return "$hour $minute $partitions"
    }

    fun toMinutes() = hour * 60 + minute

    constructor(str: List<String>) : this(str[0].toInt(), str[1].toInt(), str[2].toInt())
}
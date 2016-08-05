package com.kot.utils

/**
 * Created by wl on 16/8/5.
 */

fun main(args: Array<String>) {
    val clz = Class.forName(TestUser::class.java.name)
    var instance = TestUser::class.java.newInstance()
    val field = clz.getDeclaredField("id")
    field.isAccessible = true
    field.set(instance, "1")
    println(instance.id)
}

class TestUser {
    lateinit var id: String
    lateinit var name: String
}
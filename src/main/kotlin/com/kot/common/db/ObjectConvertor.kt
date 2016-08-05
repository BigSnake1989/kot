package com.kot.common.db

import io.vertx.ext.sql.ResultSet
import java.util.*

/**
 * Created by wl on 16/8/4.
 */
fun <T> createInstance(rs: ResultSet, clz: Class<T>): T {
    println("create instance")
    val instance = clz.newInstance()
    val results = rs.results
    println("result:" + results.toString())
    if (results.size > 0) {
        for ((index, column) in rs.columnNames.withIndex()) {
            val field = clz.getDeclaredField(column)
            field.isAccessible = true
            field.set(instance, results[0].getValue(index))
        }
        println("gen obj over")
    }
    return instance
}

fun <T> createMultiInstance(rs: ResultSet, clz: Class<T>):List<T>{
    var instanceList = ArrayList<T>()
    for (result in rs.results){
        val instance = clz.newInstance()
        for ((index, column) in rs.columnNames.withIndex()) {
            val field = clz.getDeclaredField(column)
            field.isAccessible = true
            field.set(instance, result.getValue(index))
        }
        instanceList.add(instance)
    }
    return instanceList
}
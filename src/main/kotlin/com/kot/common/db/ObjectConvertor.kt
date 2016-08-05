package com.kot.common.db

import io.vertx.ext.sql.ResultSet

/**
 * Created by wl on 16/8/4.
 */
fun <T> createInstance(rs: ResultSet, clz: Class<T>): T {
    println("create instance")
    val instance = clz.newInstance()
    val results = rs.results
    println("result:" + results.toString())
    for ((index, column) in rs.columnNames.withIndex()) {
        val field = clz.getDeclaredField(column)
        field.set(instance, results[0].getValue(index))
    }
    println("gen obj over")
    return instance
}

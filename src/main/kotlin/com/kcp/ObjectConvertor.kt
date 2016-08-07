package com.kcp

import java.sql.ResultSet
import java.util.*

/**
 * Created by wl on 16/8/4.
 */

fun <T> createInstance(rs: ResultSet, clz: Class<T>): T {
    val list = createMultiInstance(rs, clz)
    return list[list.size - 1]
}

fun <T> createMultiInstance(rs: ResultSet, clz: Class<T>): List<T> {
    var instanceList = ArrayList<T>()
    val metaData = rs.metaData
    val columnCount = metaData.columnCount
    var columnList = ArrayList<String>()
    var i = 1
    while (i <= columnCount) {
        val columnName = metaData.getColumnName(i)
        println("column name:" + columnName)
        columnList.add(columnName)
        i += 1
    }
    while (rs.next()) {
        val instance = clz.newInstance()
        for ((index, column) in columnList.withIndex()) {
            val columnName = columnList[index]
            val field = clz.getDeclaredField(columnName)
            field.isAccessible = true
            field.set(instance, rs.getString(index + 1))
        }
        instanceList.add(instance)
    }
    return instanceList
}
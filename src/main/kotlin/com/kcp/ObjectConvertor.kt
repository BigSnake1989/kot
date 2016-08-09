package com.kcp

import java.sql.ResultSet
import java.sql.Types
import java.util.*

/**
 * Created by wl on 16/8/4.
 */

fun <T> createInstance(rs: ResultSet, clz: Class<T>): T? {
    val instance = clz.newInstance()
    val metaData = rs.metaData
    val columnCount = metaData.columnCount
    val columnList = ArrayList<String>()
    var i = 1
    while (i <= columnCount) {
        val columnName = metaData.getColumnName(i)
        columnList.add(columnName)
        i += 1
    }
    var resultCount = 0
    while (rs.next()) {
        for ((index, column) in columnList.withIndex()) {
            val columnName = metaData.getColumnName(index + 1)
            val columnType = metaData.getColumnType(index + 1)
            val field = clz.getDeclaredField(columnName)
            field.isAccessible = true
            field.set(instance, getColumnValue(columnType, rs, index + 1))
        }
        resultCount++
    }
    if (resultCount ==0){
        return null
    }
    return instance
}

fun <T> createMultiInstance(rs: ResultSet, clz: Class<T>): List<T> {
    var instanceList = ArrayList<T>()
    val metaData = rs.metaData
    val columnCount = metaData.columnCount
    var columnList = ArrayList<String>()
    var i = 1
    while (i <= columnCount) {
        val columnName = metaData.getColumnName(i)
        columnList.add(columnName)
        i += 1
    }
    while (rs.next()) {
        val instance = clz.newInstance()
        for ((index, column) in columnList.withIndex()) {
            val columnName = metaData.getColumnName(index + 1)
            val columnType = metaData.getColumnType(index + 1)
            val field = clz.getDeclaredField(columnName)
            field.isAccessible = true
            field.set(instance, getColumnValue(columnType, rs, index + 1))
        }
        instanceList.add(instance)
    }
    return instanceList
}

fun getColumnValue(type: Int, rs: ResultSet, index: Int): Any? {
    var value: Any? = null
    when (type) {
        Types.INTEGER ->
            value = rs.getInt(index)
        Types.BIGINT ->
            value = rs.getLong(index)
        Types.DATE ->
            value = rs.getDate(index)
        Types.VARCHAR ->
            value = rs.getString(index)
    }
    return value
}
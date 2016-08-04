package com.kot.common

import java.util.*
import java.util.regex.Pattern

/**
 * Created by wl on 16/8/4.
 */
fun parseSQLParam(sql: String): ArrayList<String> {
    var paraList = arrayListOf("")
    val regex = "#\\{(.*?)}"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(sql)
    while (matcher.find()) {
        val r = matcher.group(1)
        paraList.add(r)
    }
    return paraList
}

fun main(args: Array<String>) {
    val sql = "id = #{id} and name = #{name} and age=#{age}"
    val regex = "#\\{(.*?)}"
    val matcher = Pattern.compile(regex).matcher(sql)
    val sb = StringBuffer()

    val map = HashMap<String, Any>()
    map.put("id", "vsdfsd")
    map.put("name", Date())
    map.put("age",20)

    while (matcher.find()) {
        val key = matcher.group(1)
        println("regex result:" + key)
        val mapValue = map[key]
        var fieldValue = ""
        when (mapValue) {
            is Date ->
                fieldValue = "'" + formatDate(mapValue) + "'"
            is String ->
                fieldValue = "'$mapValue'"
            is Int, is Long ->
                fieldValue = mapValue.toString()
        }
        matcher.appendReplacement(sb, "$fieldValue")
    }
    matcher.appendTail(sb)
    println(sb.toString())
}

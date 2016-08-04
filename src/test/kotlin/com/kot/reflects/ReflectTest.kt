package com.kot.reflects

import com.kot.common.formatDate
import io.netty.util.internal.StringUtil
import java.lang.reflect.Field
import java.util.*
import java.util.regex.Pattern

/**
 * Created by wl on 16/8/3.
 */

data class Model(var uName: String, var updateDate: Date, var minAge: Int, var maxAge: Long)

fun main(args: Array<String>) {
    var model = Model("张三", Date(), 20, 40)
    var modelClass = model.javaClass
    var fields = modelClass.declaredFields
    for (field in fields) {
        field.isAccessible = true
        println("Field Name:" + field.name + " \t\t Field Type: " + field.annotatedType.type.typeName + " \t Field Value:" + field.get(model))
    }
    println("Final Insert SQL :" + buildInsertSql(model))

    val map = HashMap<String, Any>()
    map.put("id", "vsdfsd")
    map.put("name", Date())
    map.put("age", 20)
    val sql = "id = #{id} and name = #{name} and age=#{age}"
    println("Final Update SQL :" + buildUpdateSql(model,sql,map))

}

fun buildInsertSql(obj: Any): String {
    val clz = obj.javaClass
    val tbName = clz.simpleName
    var prefix = "insert into $tbName("
    var valueStr = ""
    val fields = clz.declaredFields
    for (field in fields) {
        field.isAccessible = true
        val fieldName = convertFieldName(field.name)
        val fieldValue = getFieldValueStr(field, obj)
        prefix += "$fieldName,"
        valueStr += "$fieldValue,"
    }
    prefix = prefix.substring(0, prefix.length - 1)
    valueStr = valueStr.substring(0, valueStr.length - 1)
    return "$prefix) values($valueStr)"
}

fun buildUpdateSql(obj: Any, sql: String, map: HashMap<String, Any>): String {
    val fields = obj.javaClass.declaredFields
    val tbName = obj.javaClass.simpleName
    var prefix = "update $tbName set "
    for (field in fields) {
        field.isAccessible = true
        val fieldName = convertFieldName(field.name)
        val fieldValue = getFieldValueStr(field, obj)
        prefix += "$fieldName=$fieldValue,"
    }
    val where = getWhereStr(sql,map)
    return prefix.substring(0, prefix.length - 1) + where
}

fun getWhereStr(sql: String, paraMap: HashMap<String, Any>): String {
    if (StringUtil.isNullOrEmpty(sql)) {
        return ""
    }
    val regex = "#\\{(.*?)}"
    val matcher = Pattern.compile(regex).matcher(sql)
    val sb = StringBuffer()

    while (matcher.find()) {
        val key = matcher.group(1)
        println("regex result:" + key)
        val mapValue = paraMap[key]
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
    return " where " + sb.toString()
}

// convert field name to Camel style
fun convertFieldName(name: String): String {
    val result = StringBuilder()
    for (char in name) {
        if (char.equals(char.toUpperCase())) {
            result.append("_")
        }
        result.append(char.toLowerCase())
    }
    return result.toString()
}

// get field value according of it's type
fun getFieldValueStr(field: Field, obj: Any): String {
    val type = field.annotatedType.type.typeName
    val value = field.get(obj)
    var result = ""
    when (type) {
        "java.lang.String" ->
            result = "'$value'"
        "int", "long" ->
            result = value.toString()
        "java.util.Date" ->
            result = " '" + formatDate(value) + "' "
    }
    return result
}

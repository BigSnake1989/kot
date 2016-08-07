package com.kcp

import com.common.formatDate
import io.netty.util.internal.StringUtil
import java.lang.reflect.Field
import java.sql.SQLException
import java.util.*
import java.util.regex.Pattern

/**
 * Created by wl on 16/8/3.
 */

 class Model(){
    lateinit var uName: String
    lateinit var updateDate: Date
    lateinit var minAge: Integer
    lateinit var maxAge: Integer
}

fun main(args: Array<String>) {
    var model = Model()
    model.uName = "wang"
    model.updateDate = Date()
    model.minAge = Integer(10)
    model.maxAge = Integer(20)
    var modelClass = model.javaClass
    var fields = modelClass.declaredFields
    for (field in fields) {
        field.isAccessible = true
        println("Field Name:" + field.name + " \t\t Field Type: " + field.annotatedType.type.typeName + " \t Field Value:" + field.get(model))
    }
    println("Final Insert SQL :" + buildInsertSql(model))

    val clz = Model::class.java
    val map = HashMap<String, Any>()
    map.put("id", "myId")
    map.put("name", Date())
    map.put("age", 20)
    val sql = "id = #{id} and name = #{name} and age=#{age}"
    println("Final Update SQL :" + buildUpdateSql(model, sql, map))
    println("Final Delete SQL :" + buildDeleteSql(model, sql, map))
    println("Final Query  SQL :" + buildQuerySql(clz, sql, map))
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
        var fieldValue = getFieldValueStr(field, obj)
        if ("".equals(fieldValue)){
            fieldValue = "null"
        }
        prefix += "$fieldName,"
        valueStr += "$fieldValue,"
    }
    prefix = prefix.substring(0, prefix.length - 1)
    valueStr = valueStr.substring(0, valueStr.length - 1)
    return "$prefix) values($valueStr)"
}

fun buildUpdateSql(obj: Any, sql: String?, map: HashMap<String, Any>?): String {
    val fields = obj.javaClass.declaredFields
    val tbName = obj.javaClass.simpleName
    var prefix = "update $tbName set "
    for (field in fields) {
        field.isAccessible = true
        val fieldName = convertFieldName(field.name)
        val fieldValue = getFieldValueStr(field, obj)
        prefix += "$fieldName=$fieldValue,"
    }
    val where = getWhereStr(sql, map)
    return prefix.substring(0, prefix.length - 1) + where
}

fun buildDeleteSql(obj: Any, sql: String?, map: HashMap<String, Any>?): String {
    val tbName = obj.javaClass.simpleName
    val prefix = "delete from $tbName "
    val where = getWhereStr(sql, map)
    return prefix + where
}

fun <T> buildQuerySql(obj: Class<T>, sql: String?, map: HashMap<String, Any>?): String {
    val fields = obj.declaredFields
    val tbName = obj.simpleName
    var prefix = "select "
    for (field in fields) {
        field.isAccessible = true
        val fieldName = convertFieldName(field.name)
        prefix += " $fieldName as ${field.name},"
    }
    prefix = prefix.substring(0, prefix.length - 1) + " from $tbName"
    val where = getWhereStr(sql, map)
    return prefix + where
}

private fun getWhereStr(sql: String?, paraMap: HashMap<String, Any>?): String {
    if ("".equals(sql) || sql == null) {
        return ""
    }
    if (paraMap == null){
        throw SQLException("Parameter Map Can Not Be Empty When Your Where Sql Is Not Empty")
    }
    val regex = "#\\{(.*?)}"
    val matcher = Pattern.compile(regex).matcher(sql)
    val sb = StringBuffer()

    while (matcher.find()) {
        val key = matcher.group(1)
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
    return " where " + sb.toString()
}

// convert field name to Camel style
private fun convertFieldName(name: String): String {
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
private fun getFieldValueStr(field: Field, obj: Any): String {
    val type = field.annotatedType.type.typeName
    val value = field.get(obj)
    var result = ""
    when (type) {
        "java.lang.String" ->
            result = "'$value'"
        "java.lang.Integer", "java.lang.Long" ->
            if (value == null){
                result = "null"
            }else{
                result = value.toString()
            }
        "java.util.Date" ->
            result = " '" + formatDate(value) + "' "
    }
    return result
}

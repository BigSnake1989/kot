package com.kot.reflects

import java.util.*

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
    println("Final:"+ buildInsertSql(model))
}

fun buildInsertSql(obj: Any): String {
    var clz = obj.javaClass
    var tbName = clz.simpleName
    var prefix = "INSERT INTO $tbName("
    var fields = clz.declaredFields
    for (field in fields) {
        field.isAccessible = true
        prefix += formatFieldName(field.name)+ ","
    }
    return prefix
}

fun formatFieldName(name: String): String {
    var result = StringBuilder()
    for (char in name){
        if (char.equals(char.toUpperCase())){
            result.append("_")
        }
        result.append(char.toLowerCase())
    }
    return result.toString()
}

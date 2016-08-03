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
        println("Field Name:" + field.name + " \t\t Field Type: " + field.annotatedType.type.typeName + " \t Field Value:" + field.get(model) )
    }
}

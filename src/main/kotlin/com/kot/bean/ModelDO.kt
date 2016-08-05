package com.kot.bean

import java.util.*

/**
 * Created by wl on 16/7/30.
 */
class User() {
    lateinit var id: String
    lateinit var name: String
}

data class Topic(
        var id: Long,
        var topicName: String,
        var category: String,
        var content: String,
        var userId: Long,
        var createTime: Date,
        var updateTime: Date
)
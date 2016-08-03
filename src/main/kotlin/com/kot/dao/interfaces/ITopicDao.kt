package com.kot.dao.interfaces

import com.kot.bean.Topic
import io.vertx.core.Future

/**
 * Created by wl on 16/8/3.
 */
interface ITopicDao {
    fun addTopic(topic: Topic): Future<Unit>
    fun getTopicList(vararg param: Any): Future<List<Topic>>
    fun getTopic(id: Int): Future<Topic>
}
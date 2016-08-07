package com.kot.dao

import com.kot.bean.Topic
import com.kcp.update
import com.kot.dao.interfaces.ITopicDao
import io.vertx.core.Future
import io.vertx.ext.jdbc.JDBCClient

/**
 * Created by wl on 16/8/3.
 */
class TopicDao(val client: JDBCClient) : ITopicDao {
    override fun addTopic(topic: Topic): Future<Unit> {
        val sql = "insert into topic(topic_name,category,content,user_id,create_time,update_time) values(?,?,?,?,?,?)"
        return client.update(sql, listOf(topic))
    }

    override fun getTopicList(vararg param: Any): Future<List<Topic>> {
        throw UnsupportedOperationException()
    }

    override fun getTopic(id: Int): Future<Topic> {
        throw UnsupportedOperationException()
    }

}
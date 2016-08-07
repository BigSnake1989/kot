package com.kot.dao

import com.kot.bean.Topic
import com.kot.dao.interfaces.ITopicDao
import io.vertx.core.Future
import io.vertx.ext.jdbc.JDBCClient

/**
 * Created by wl on 16/8/3.
 */
class TopicDao(val client: JDBCClient) : ITopicDao {
    override fun addTopic(topic: Topic): Future<Unit> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTopicList(vararg param: Any): Future<List<Topic>> {
        throw UnsupportedOperationException()
    }

    override fun getTopic(id: Int): Future<Topic> {
        throw UnsupportedOperationException()
    }

}
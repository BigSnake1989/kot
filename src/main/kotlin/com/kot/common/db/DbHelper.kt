package com.kot.common.db

import io.vertx.core.Future
import io.vertx.ext.jdbc.JDBCClient
import java.util.*

/**
 * Created by wl on 16/8/4.
 */
class DbHelper {
    companion object {
        fun <T> queryOne(client: JDBCClient, model: Class<T>, sql: String, para: HashMap<String, Any>): Future<T> {
            println("begin query one")
            val future = Future.future<T>()
            val querySql = buildQuerySql(model, sql, para)
            println("sql :" + querySql)
            client.queryOne(querySql, listOf()) {
                println("client query over")
                val finished = Future.future<Unit>()
                future.complete(createInstance(it, model))
                println("get final obj")
                finished
            }
            return future
        }
    }
}
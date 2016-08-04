package com.kot.common.db

import io.vertx.core.Future
import io.vertx.ext.jdbc.JDBCClient
import java.util.*

/**
 * Created by wl on 16/8/4.
 */
class DbHelper {
    companion object {
        fun queryOne(client: JDBCClient, model: Any, sql: String, para: HashMap<String, Any>): Future<Any> {
            var future = Future.future<Any>()
            val querySql = buildQuerySql(model!!, sql, para)
            client.queryOne(querySql, listOf(), { resultSet ->
                genObj(resultSet, model.javaClass)
                future.complete(model)
            })
            return future
        }

    }
}
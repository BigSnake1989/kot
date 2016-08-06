package com.kot.common.db

import io.vertx.core.Future
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.sql.ResultSet
import java.util.*

/**
 * Created by wl on 16/8/4.
 */
class DbHelper {
    companion object {
        fun <T> queryOne(client: JDBCClient, clz: Class<T>, sql: String, para: HashMap<String, Any>): Future<T> {
            println("begin query one")
            val future = Future.future<T>()
            val querySql = buildQuerySql(clz, sql, para)
            println("sql :" + querySql)
            client.query(querySql, listOf()) {
                println("client query over")
                val finished = Future.future<Unit>()
                future.complete(createInstance(it, clz))
                println("get final obj")
                finished
            }
            return future
        }

        fun <T> queryList(client: JDBCClient,clz:Class<T>,sql: String,para: HashMap<String, Any>):Future<List<T>>{
            val future = Future.future<List<T>>()
            val querySql = buildQuerySql(clz,sql,para)
            client.query(querySql, listOf()){
                val finished = Future.future<Unit>()
                future.complete(createMultiInstance(it,clz))
                finished
            }
            return future
        }

//        fun <T> queryList(client: JDBCClient, clz: Class<T>, sql: String, para: HashMap<String, Any>): Future<List<T>> {
//            val future = Future.future<List<T>>()
//            val querySql = buildQuerySql(clz, sql, para)
//            client.queryList(querySql, listOf()){
//              TODO This will occurred some error , but why ? I don't know
//            }
//            return future
//        }
    }

}
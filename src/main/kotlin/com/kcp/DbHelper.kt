package com.kcp

import io.vertx.core.Future
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.sql.ResultSet
import java.util.*

/**
 * Created by wl on 16/8/4.
 */
class DbHelper {
    companion object {

        fun insert(model: Any): Long {
            val querySql = buildInsertSql(model)
            return JdbcClient.insert(querySql)
        }

        fun <T> queryOne(clz: Class<T>, whereCondit: String, para: HashMap<String, Any>): T {
            println("begin query one")
            val querySql = buildQuerySql(clz, whereCondit, para)
            println("sql :" + querySql)
            val resultSet = JdbcClient.query(querySql)
            val instance = createInstance(resultSet,clz)
            return instance
        }

        fun <T> queryList(clz:Class<T>, whereCondit: String, para: HashMap<String, Any>): List<T> {
            val querySql = buildQuerySql(clz,whereCondit,para)
            println("query sql:"+querySql)
            val resultSet = JdbcClient.query(querySql)
            return createMultiInstance(resultSet,clz)
        }

        fun update(client: JDBCClient, model:Any, whereCondit: String, para: HashMap<String, Any>): Future<Unit> {
            val future = Future.future<Unit>()
            val querySql = buildUpdateSql(model,whereCondit,para)
            client.executeSql(querySql){
                val finished = Future.future<Unit>()
                future.complete()
                finished
            }
            return future
        }

        fun delete(client: JDBCClient, model: Any, whereCondit:String, para: HashMap<String, Any>): Future<Unit> {
            val querySql = buildDeleteSql(model,whereCondit,para)
            return client.execute(querySql)
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
package com.kcp

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

        fun <T> queryOne(clz: Class<T>, whereCondition: String, para: HashMap<String, Any>): T? {
            val querySql = buildQuerySql(clz, whereCondition, para)
            val resultSet = JdbcClient.query(querySql)
            val instance = createInstance(resultSet, clz)
            return instance
        }

        fun <T> queryList(clz: Class<T>, whereCondition: String, para: HashMap<String, Any>): List<T> {
            val querySql = buildQuerySql(clz, whereCondition, para)
            val resultSet = JdbcClient.query(querySql)
            return createMultiInstance(resultSet, clz)
        }

        fun update(model: Any, whereCondition: String?, para: HashMap<String, Any>?) {
            val querySql = buildUpdateSql(model, whereCondition, para)
            JdbcClient.execute(querySql)
        }

        fun delete(model: Any, whereCondition: String?, para: HashMap<String, Any>?) {
            val querySql = buildDeleteSql(model, whereCondition, para)
            JdbcClient.execute(querySql)
        }
    }
}
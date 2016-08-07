package com.kcp

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.io.PrintWriter
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement
import java.util.*

/**
 * Created by wl on 16/8/7.
 */

fun main(args: Array<String>) {

    val updateSql = "update user set name='LLLL' where id =6"
    JdbcClient.execute(updateSql)

    val deleteSql = "delete from user where id =6"
    JdbcClient.execute(deleteSql)

    val insertSql = "insert into user(name,avatar) values('Miao','maomi')"
    val insertResult = JdbcClient.insert(insertSql)
    println("insert result:" + insertResult)

    val querySql = "select * from user"
    val result = JdbcClient.query(querySql)
    while (result.next()){
        println(result.getString(2))
    }

}

class JdbcClient {
    companion object {

        fun insert(sql: String): Long {
            return withConnection {
                val stmt = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                stmt.executeUpdate()
                val result = stmt.generatedKeys
                var id: Long = 0
                if (result.next()) {
                    id = result.getLong(1)
                }
                id
            } as Long
        }

        fun query(sql: String):ResultSet {
            return withConnection {
                val stmt = it.prepareStatement(sql)
                val result = stmt.executeQuery()
                result
            } as ResultSet
        }

        fun execute(sql: String): Any? {
            return withConnection {
                it.prepareStatement(sql).executeUpdate()
            }
        }

        fun withConnection(res: (Connection) -> Any?): Any? {
            val conn = getConnection()
            conn.autoCommit = true
//            TODO What will happen if I don't close this statement and resultSet ?
//            TODO Maybe occurred some error even if I have connection pool ?
            return res(conn)
        }

        fun getConnection(): Connection {
            val props = Properties()
            props.setProperty("dataSourceClassName", "com.mysql.jdbc.jdbc2.optional.MysqlDataSource")
            props.setProperty("dataSource.user", "root")
            props.setProperty("dataSource.password", "123456")
            props.setProperty("dataSource.databaseName", "kot")
            props.put("dataSource.logWriter", PrintWriter(System.out))
            val config = HikariConfig(props)
            val ds = HikariDataSource(config)
            val conn = ds.connection
            return conn
        }
    }
}
package com.kcp

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.Test
import java.io.PrintWriter
import java.sql.Connection
import java.sql.ResultSet
import java.util.*

/**
 * Created by wl on 16/8/7.
 */
class PoolConnection{

    @Test
    fun doQuery(){
        val data = arrayListOf("")
        withConnection {
            val stmt = it.createStatement()
            val result = stmt.executeQuery("select * FROM user")
            while (result.next()){
                println("result:"+result.getString(2))
                data.add(result.getString(2))
            }
            result
        }
        println("Final Data:"+data.toString())
    }

    fun withConnection(res:(Connection)-> ResultSet){
        val conn = getConnection()
        val result = res(conn)
        if (result != null){
            result.close()
            result.statement.close()
        }else{
            println("Result Set is empty or null,Please Check")
        }
    }

    fun getConnection():Connection{
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

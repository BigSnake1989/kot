package com.kot

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.asyncsql.MySQLClient
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(VertxUnitRunner::class)
class DbTest {

    @Test
    fun test_async(c: TestContext) {
        val client = Vertx.vertx().createHttpClient()
        val async = c.async()
        client.getNow(80, "www.baidu.com", "/", { res ->
            res.bodyHandler({ body ->
                client.close()
                async.complete()
            })
        })
        async.awaitSuccess()
    }

    @Test
    fun test_connection(c: TestContext) {
        val async = c.async()
        val client = MySQLClient.createShared(Vertx.vertx(), JsonObject()
                .put("host", "127.0.0.1")
                .put("port", 3306)
                .put("username", "root")
                .put("password", "123456")
                .put("database", "kot"))

        println("ready connect...")
        client.getConnection({ conn ->

            if (conn.failed()) {
                println("connect error :" + conn.cause().message)
                System.err.println(conn.cause().message)
                return@getConnection
            }
            println("connect success")
            val connection = conn.result()
            connection.setAutoCommit(false, { res ->
                connection.execute("create table tt(id int primary key, name varchar(255))", { r ->
                    if (r.failed()) {
                        println("create fail :" + r.cause().message)
                    }
                    c.assertTrue(r.succeeded())

                    println("create success")
                    async.complete()
                })
            })

        })
    }
}


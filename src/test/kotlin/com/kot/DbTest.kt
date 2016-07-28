package com.kot

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(VertxUnitRunner::class)
class DbTest {

    val client = JDBCClient.createShared(Vertx.vertx(), JsonObject()
            .put("provider_class","io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider")
            .put("jdbcUrl", "jdbc:mysql://localhost:3306/kot?useSSL=false")
            .put("driver_class", "com.mysql.jdbc.Driver")
            .put("username", "root")
            .put("password", "123456")
            .put("max_pool_size", 30))

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
        println("ready connect...")
        client.getConnection({ conn ->

            if (conn.failed()) {
                println("connect error :" + conn.cause().message)
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
                    connection.execute("insert into tt(id,name) values(1,'haha')",{insert ->
                        if (insert.failed()){
                            println("insert :"+ insert.cause().message)
                        }
                        c.assertTrue(insert.succeeded())
                        connection.commit({commit ->
                            c.assertTrue(commit.succeeded())
                        })
                        async.complete()
                    })
                    println("create success")
                })
            })

        })
    }

    @After
    fun setDown(ctx: TestContext){
        client.getConnection({conn ->
            if (conn.succeeded()){
                val connection = conn.result()
                connection.execute("drop table tt",{r ->
                    ctx.assertTrue(r.succeeded())
                })
            }
        })
    }
}


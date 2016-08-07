package com.kot.dao

import com.kot.bean.User
import com.kcp.DbHelper
import com.kot.dao.interfaces.IUserDao
import com.kot.dao.UserDao
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.unit.Async
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by wl on 16/7/30.
 */

@RunWith(VertxUnitRunner::class)
class UserDaoTest {

    @Test
    fun testClass() {
        val clz = User::class.java
        val sql = "avatar=#{id}"
        val para = HashMap<String, Any>()
        para.put("id", "hehe")
        val user = DbHelper.queryOne(clz, sql, para)
        println("final user:" + user.toString())
    }

    @Test
    fun testMuli(){
        val sql = "avatar=#{id}"
        val para = HashMap<String, Any>()
        para.put("id", "hehe")
        val user = DbHelper.queryList(User::class.java, sql, para)
        println("final user:" + user.toString())
    }


    @Test
    fun testUserAdd(ctx: TestContext) {
        val async = ctx.async()
        val userService = UserDao(client)
        val user = User("wang", "lei")
//        user.id = "wang"
//        user.name = "lei"
        val future = userService.addUser(user)
        future.setHandler {
            ctx.assertTrue(it.succeeded(), "增加user失败")
            async.complete()
        }
    }

    @Test
    fun testUserQuery(ctx: TestContext) {
        val async = ctx.async()
        val userService = UserDao(client)
        val userFuture = userService.getUser("wang")
        userFuture.setHandler {
            if (it.succeeded()) {
                val user = it.result()
                println("user info:" + user.toString())
                ctx.assertNotNull(user, "查询失败")
            } else {
                print("--------- ERROR ---------")
            }
            async.complete()
        }
    }

    @Test
    fun testUserRm(ctx: TestContext) {
        val async = ctx.async()
        val userService = UserDao(client)
        val future = userService.rmUser("wang")
        future.setHandler {
            ctx.assertTrue(it.succeeded(), "删除失败")
            async.complete()
        }
    }

    companion object {
        val client = JDBCClient.createShared(Vertx.vertx(), JsonObject()
                .put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider")
                .put("jdbcUrl", "jdbc:mysql://localhost:3306/kot?useSSL=false")
                .put("driver_class", "com.mysql.jdbc.Driver")
                .put("username", "root")
                .put("password", "123456")
                .put("max_pool_size", 30))
    }
}
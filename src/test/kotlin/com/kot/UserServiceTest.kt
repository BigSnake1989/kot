package com.kot

import com.kot.bean.User
import com.kot.service.IUserService
import com.kot.service.UserService
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.unit.Async
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by wl on 16/7/30.
 */

@RunWith(VertxUnitRunner::class)
class UserServiceTest{


    @Test
    fun testUserAdd(ctx:TestContext){
        val userService = UserService(client)
        val user = User("wang","hahaha")
        val future = userService.addUser(user)
        future.setHandler {
            ctx.assertTrue(it.succeeded(),"增加user失败")
        }
    }

    @Test
    fun testUserQuery(ctx: TestContext){
        val async = ctx.async()
        val userService = UserService(client)
        val userFuture = userService.getUser("wang")
        userFuture.setHandler {
            if (it.succeeded()){
                val user = it.result()
                println("user:"+user.toString())
                ctx.assertNotNull(user,"查询失败")
            }else{
                print("--------- ERROR ---------")
            }
            async.complete()
        }
    }

    @Test
    fun testUserRm(ctx: TestContext){
        val userService = UserService(client)
        val future = userService.rmUser("wang")
        future.setHandler {
            ctx.assertTrue(it.succeeded(),"删除失败")
        }
    }

    companion object {
        val client = JDBCClient.createShared(Vertx.vertx(), JsonObject()
                .put("provider_class","io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider")
                .put("jdbcUrl", "jdbc:mysql://localhost:3306/kot?useSSL=false")
                .put("driver_class", "com.mysql.jdbc.Driver")
                .put("username", "root")
                .put("password", "123456")
                .put("max_pool_size", 30))
    }
}
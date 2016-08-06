package com.kot.common

import com.kot.bean.User
import com.kot.common.db.DbHelper
import com.kot.dao.UserDaoTest
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by wl on 16/8/5.
 */
@RunWith(VertxUnitRunner::class)
class DbHelperTest {

    @Test
    fun test_query_one(ctx: TestContext) {
        val async = ctx.async()
        val sql = "avatar=#{id}"
        val para = HashMap<String, Any>()
        para.put("id", "hehe")
        val userFuture = DbHelper.queryOne(UserDaoTest.client, User::class.java, sql, para)
        println("get user future:" + userFuture.toString())
        userFuture.setHandler {
            if (it.succeeded()) {
                println("user:" + it.result())
                println("user id:" + it.result().id)
                println("user name:" + it.result().name)
            } else {
                print("Get User Error")
            }
            async.complete()
        }
    }

    @Test
    fun test_query_list(ctx: TestContext){
        val async = ctx.async()
        val sql = "avatar=#{id}"
        val para = HashMap<String, Any>()
        para.put("id", "hehe")
        val userFuture = DbHelper.queryList(UserDaoTest.client, User::class.java, sql, para)
        println("get user future:" + userFuture.toString())
        userFuture.setHandler {
            if (it.succeeded()) {
                println("user list:" + it.result())
            } else {
                print("Get User Error")
            }
            async.complete()
        }
    }
}
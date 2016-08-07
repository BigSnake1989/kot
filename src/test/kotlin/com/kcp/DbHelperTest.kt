//package com.kcp
//
//import com.kot.bean.User
//import com.kcp.DbHelper
//import com.kcp.execute
//import com.kcp.insert
//import com.kot.dao.UserDaoTest
//import io.vertx.ext.unit.TestContext
//import io.vertx.ext.unit.junit.VertxUnitRunner
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.util.*
//
///**
// * Created by wl on 16/8/5.
// */
//@RunWith(VertxUnitRunner::class)
//class DbHelperTest {
//
//    @Test
//    fun test_query_one(ctx: TestContext) {
//        val async = ctx.async()
//        val sql = "avatar=#{id}"
//        val para = HashMap<String, Any>()
//        para.put("id", "hehe")
//        val userFuture = DbHelper.queryOne(UserDaoTest.client, User::class.java, sql, para)
//        println("get user future:" + userFuture.toString())
//        userFuture.setHandler {
//            if (it.succeeded()) {
//                println("user:" + it.result())
//                println("user id:" + it.result().id)
//                println("user name:" + it.result().name)
//            } else {
//                print("Get User Error")
//            }
//            async.complete()
//        }
//    }
//
//    @Test
//    fun test_query_list(ctx: TestContext) {
//        val async = ctx.async()
//        val sql = "avatar=#{id}"
//        val para = HashMap<String, Any>()
//        para.put("id", "hehe")
//        val userFuture = DbHelper.queryList(UserDaoTest.client, User::class.java, sql, para)
//        println("get user future:" + userFuture.toString())
//        userFuture.setHandler {
//            if (it.succeeded()) {
//                println("user list:" + it.result())
//            } else {
//                print("Get User Error")
//            }
//            async.complete()
//        }
//    }
//
//    @Test
//    fun test_update(ctx: TestContext) {
//        val user = User("1", "M.LO", "hehe")
//        val sql = "id =#{id}"
//        val para = hashMapOf(Pair<String, Any>("id", user.id))
//        val future = DbHelper.update(UserDaoTest.client, user, sql, para)
//        future.setHandler {
//            println("update:"+it.result())
//            ctx.async().complete()
//        }
//    }
//
//    @Test
//    fun test_update_client(ctx: TestContext){
//        val future = UserDaoTest.client.execute("select  id as id, name as name, avatar as avatar from User where avatar='hehe'")
//        future.setHandler {
//            println("result:"+it)
//            ctx.async().complete()
//        }
//    }
//
//    @Test
//    fun test_delete(ctx: TestContext) {
//        val sql = "id =#{id}"
//        val para = hashMapOf(Pair<String, Any>("id", "3"))
//        val future = DbHelper.delete(UserDaoTest.client, User(), sql, para)
//        future.setHandler {
//            println("result delete" + it.result())
//            ctx.async().complete()
//        }
//    }
//
//    @Test
//    fun test_insert(ctx: TestContext) {
//        val future = UserDaoTest.client.insert("insert into user(id,name) values(3,'hhahsdf')")
//        future.setHandler {
//            println("result:" + it.result())
//            ctx.async().complete()
//        }
//    }
//}
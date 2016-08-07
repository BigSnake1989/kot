package com.kcp

import com.kot.bean.User
import org.junit.Test
import java.util.*

/**
 * Created by wl on 16/8/7.
 */
class TestDbHelper {

    @Test
    fun testInsert(){
        val user = User()
        user.name="meiqiu"
        user.avatar="black"
        DbHelper.insert(user)
    }

    @Test
    fun testQueryOne() {
        val clz = User::class.java
        val where = "name=#{avatar}"
        val para = HashMap<String, Any>()
        para.put("avatar", "meiqiu")
        val user = DbHelper.queryOne(clz, where, para)
        println("final user:" + user.toString())
    }

    @Test
    fun testQueryList(){
        val where = "name=#{avatar}"
        val para = HashMap<String, Any>()
        para.put("avatar", "meiqiu")
        val user = DbHelper.queryList(User::class.java, where, para)
        println("final user:" + user.toString())
    }

    @Test
    fun testUpdate(){
        val where = "name=#{avatar}"
        val para = HashMap<String, Any>()
        para.put("avatar", "meiqiu")
        val user = User(10,"meiqiu","hehe")
        DbHelper.update(user,where,para)
    }

    @Test
    fun testDelete(){
        val user = User(10,"meiqiu","hehe")
        val where = "name=#{avatar}"
        val para = HashMap<String, Any>()
        para.put("avatar", "meiqiu")
        DbHelper.delete(user,where,para)
    }

}
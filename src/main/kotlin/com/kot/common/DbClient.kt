//package com.kot.common
//
//import io.vertx.core.Vertx
//import io.vertx.core.json.JsonObject
//import io.vertx.ext.jdbc.JDBCClient
//
///**
// * Created by wl on 16/7/27.
// */
//object DbClient {
//    fun mysql(v : Vertx) : JDBCClient {
//        val config = JsonObject().
//                put("url","jdbc:mysql://127.0.0.1:3306/kot").
//                put("driver_class","com.mysql.jdbc.Driver").
//                put("max_pool_size", 20)
//        return JDBCClient.createShared(v,config)
//    }
//}
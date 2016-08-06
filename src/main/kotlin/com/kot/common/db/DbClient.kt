package com.kot.common.db

import io.vertx.core.Future
import io.vertx.core.json.JsonArray
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.sql.ResultSet
import io.vertx.ext.sql.SQLConnection

/**
 * Created by wl on 16/7/30.
 */

//get connection
fun JDBCClient.withConnection(res: (SQLConnection) -> Future<Unit>): Future<Unit> {
    val finished = Future.future<Unit>()
    getConnection {
        if (it.succeeded()) {
            val connection = it.result()
            val done = res(connection)
            done.setHandler {
                connection.close()
                if (it.succeeded()) finished.complete()
                else finished.fail(it.cause())
            }
        } else {
            finished.fail(it.cause())
        }
    }
    return finished
}

fun <T> JDBCClient.queryList(query: String, params: List<Any>, rsHandler: (ResultSet) -> List<T>): Future<List<T>> {
    val future = Future.future<List<T>>()
    withConnection {
        val finished = Future.future<Unit>()
        it.queryWithParams(query, JsonArray(params), {
            if (it.succeeded()) {
                try {
                    val result = rsHandler(it.result())
                    future.complete(result)
                } catch (t: Throwable) {
                    future.fail(t)
                } finally {
                    finished.complete()
                }
            } else {
                finished.fail(it.cause())
            }
        })
        finished
    }
    return future
}

fun <T> JDBCClient.query(query: String, params: List<Any>, rsHandler: (ResultSet) -> T): Future<T> {
    val future = Future.future<T>()
    println("begin client query one")
    withConnection {
        val finished = Future.future<Unit>()
        it.queryWithParams(query, JsonArray(params), {
            if (it.succeeded()) {
                try {
                    val result = rsHandler(it.result())
                    future.complete(result)
                } catch (t: Throwable) {
                    println("error:" + t.message)
                    t.printStackTrace()
                    future.fail(t)
                } finally {
                    finished.complete()
                }
            } else {
                it.cause().printStackTrace()
                finished.fail(it.cause())
            }
        })
        finished
    }
    return future
}

fun JDBCClient.update(query: String, params: List<Any>): Future<Unit> {
    val future = withConnection {
        val finished = Future.future<Unit>()
        it.updateWithParams(query, JsonArray(params), {
            if (it.succeeded()) {
                finished.complete()
            } else {
                println("error:" + it.cause())
                finished.fail(it.cause())
            }
        })
        finished
    }
    return future
}

fun JDBCClient.execute(query: String): Future<Unit> {
    val future = withConnection {
        val finished = Future.future<Unit>()
        it.execute(query, {
            if (it.succeeded()) {
                finished.complete()
            } else {
                finished.fail(it.cause())
            }
        })
        finished
    }
    return future
}
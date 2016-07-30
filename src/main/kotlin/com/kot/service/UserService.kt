package com.kot.service

import com.kot.bean.User
import com.kot.common.execute
import com.kot.common.queryOne
import com.kot.common.update
import io.vertx.core.Future
import io.vertx.ext.jdbc.JDBCClient

/**
 * Created by wl on 16/7/30.
 */
class UserService(val client: JDBCClient) : IUserService {

    override fun getUser(id: String): Future<User> {
        return client.queryOne("select id,name from users where id=?", listOf(id)){
            it.results.map { User(it.getString(0),it.getString(1)) }.first()
        }
    }

    override fun addUser(user: User): Future<Unit> {
        return client.update("insert into users(id,name) values(?,?)", listOf(user.id,user.name))
    }

    override fun rmUser(id: String): Future<Unit> {
        return client.update("delete from users where id = ?", listOf(id))
    }

}
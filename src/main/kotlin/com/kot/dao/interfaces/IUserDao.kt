package com.kot.dao.interfaces

import com.kot.bean.User
import io.vertx.core.Future

/**
 * Created by wl on 16/7/30.
 */
interface IUserDao {
    fun getUser(id: String): Future<User>
    fun addUser(user: User): Future<Unit>
    fun rmUser(id: String): Future<Unit>
}
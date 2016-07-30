package com.kot.service

import com.kot.bean.User
import io.vertx.core.Future

/**
 * Created by wl on 16/7/30.
 */
interface IUserService {
    fun getUser(id: String): Future<User>
    fun addUser(user: User): Future<Unit>
    fun rmUser(id: String): Future<Unit>
}
package com.kot.router

import io.vertx.ext.web.Router

/**
 * Created by wl on 16/7/31.
 */

fun Router.userRouter(r: Router) {
    r.post("/user/register").handler { rc ->
        rc.response().text().end("user register")
    }

    r.get("/user/info").handler { rc ->
        rc.response().text().end("get user info")
    }
}
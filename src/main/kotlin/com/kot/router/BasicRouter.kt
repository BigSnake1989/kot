package com.kot.router

import com.kot.bean.Entity
import com.kot.common.html
import com.kot.common.json
import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router

/**
 * Created by wl on 16/7/22.
 */

fun customRouter(v : Vertx) : Router {
    var router = Router.router(v)
    router.route("/hello").handler({c -> c.response().html().end("hello world")})
    router.route("/json").handler({c -> c.response().json().end(Json.encode(Entity("name","hehe")))})
    router.userRouter(router)
    return router
}

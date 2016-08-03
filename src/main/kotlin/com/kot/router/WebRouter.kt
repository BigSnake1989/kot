package com.kot.router

import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.templ.ThymeleafTemplateEngine
import org.thymeleaf.templatemode.TemplateMode

/**
 * Created by wl on 16/7/26.
 *
 */
fun renderPage(r: Router) {
    val engine = ThymeleafTemplateEngine.create().setMode(TemplateMode.HTML)
    r.route("/static/*").handler(StaticHandler.create("static").setCachingEnabled(false))
    r.get("/").handler({ c -> index(c, engine) })
    r.get("/index.html").handler({ c -> index(c, engine) })
}

fun index(c: RoutingContext, engine: ThymeleafTemplateEngine) {
    c.put("welcome", "hello world,page !!")
    var list = arrayListOf("hehe","haha")
    c.put("lists",list)
    response(c, engine, "templates/home/index.html")
}

fun response(c: RoutingContext, engine: ThymeleafTemplateEngine, tpl: String) {
    engine.render(c, tpl, { res ->
        if (res.succeeded()) {
            c.response().end(res.result())
        } else {
            c.fail(res.cause())
        }
    })
}
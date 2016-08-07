package com.common

import io.vertx.core.http.HttpServerResponse

/**
 * Created by wl on 16/8/1.
 */

fun HttpServerResponse.html() : HttpServerResponse {
    return this.putHeader("content-type","text/html")
}

fun HttpServerResponse.text(): HttpServerResponse {
    return this.putHeader("content-type","text/plain");
}

fun HttpServerResponse.json() : HttpServerResponse {
    return this.putHeader("content-type","application/json; charset=utf-8")
}

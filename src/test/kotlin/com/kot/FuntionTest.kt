package com.kot

/**
 * Created by wl on 16/8/6.
 */
fun main(args: Array<String>) {
    withConnection {
        println("begin db op,get Conn:"+it.toString())
        true
    }
}

fun withConnection(res:(Conn)->Boolean){
    val conn = Client.getConnection()
    val result = res(conn)
    if (result){
        conn.close()
    }else{
        println("Db Op Error")
    }
}

class Client{
    companion object{
        fun getConnection():Conn{
            return Conn("CID","My ID")
        }
    }
}

data class Conn(var connId:String="",var msg:String="")

fun Conn.close(){
    println("Conn closed")
}
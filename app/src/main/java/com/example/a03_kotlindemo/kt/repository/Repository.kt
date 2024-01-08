package com.example.a03_kotlindemo.kt.repository

import com.example.a03_kotlindemo.kt.Register
import com.example.a03_kotlindemo.kt.registerApi
import java.net.SocketException

class Repository {
    suspend fun toRegister(name: String): Register {
        var _reshult: Register = Register("访问失败", -1)
        try {
            _reshult = registerApi.postRegister(name)
        } catch (e: SocketException) {
            println(e)
            return _reshult
        }
        return _reshult
    }
}
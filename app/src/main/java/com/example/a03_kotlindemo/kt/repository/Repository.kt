package com.example.a03_kotlindemo.kt.repository

import com.example.a03_kotlindemo.kt.*

class Repository {
    suspend fun toRegister(name: String): Register {
        var _result: Register? = null
        try {
            _result = registerApi.postRegister(name)
        } catch (e: Exception) {
            println(e)
        }
        return _result ?: Register()
    }

    suspend fun getWisdom(): WisdomData {
        var _result: WisdomResponse<WisdomData>? = null
        try {
            _result = wisdomApi.getWisdom()
        } catch (e: Exception) {
            println(e)
        }
        return (_result ?: WisdomResponse(data = null)).data ?: WisdomData()
    }
}
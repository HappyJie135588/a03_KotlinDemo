package com.example.a03_kotlindemo.kt.repository

import com.example.a03_kotlindemo.kt.Register
import com.example.a03_kotlindemo.kt.registerApi

class Repository {
    suspend fun toRegister(name: String): Register? {
        return registerApi.postRegister(name)
    }
}
package com.example.a03_kotlindemo.kt

import com.example.a03_kotlindemo.network.retrofit.api.RetrofitUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

//{"msg":"注册成功","code":200}
data class Register(val msg: String, val code: Int) {

}

val registerApi: RegisterApi by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(RetrofitUrl.autumnfish)
//        .addConverterFactory(MoshiConverterFactory.create())//两种都可以返回Obj
        .addConverterFactory(GsonConverterFactory.create())//两种都可以Obj
//        .addConverterFactory(ScalarsConverterFactory.create())//直接返回基本类型
        .build()
    retrofit.create(RegisterApi::class.java)
}

interface RegisterApi {
    @POST("user/register")
    @FormUrlEncoded
    suspend fun postRegister(@Field("username") username: String): Register
}
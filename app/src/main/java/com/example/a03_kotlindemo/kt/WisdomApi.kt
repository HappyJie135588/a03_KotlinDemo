package com.example.a03_kotlindemo.kt

import com.example.a03_kotlindemo.network.retrofit.api.RetrofitUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//{
//    "code": 200,
//    "data": {
//    "id": 1993,
//    "tag": "写给黄淮",
//    "name": "佚名",
//    "origin": "解忧邵帅",
//    "content": "老版写给黄淮继续以demo形式存在，我这会儿特别激动，大家愿意听我的歌儿我超开心。这几个月我看了太多的私信和留言评论，录正式版的时候把我听到的故事揉碎了唱进歌儿里，好多让我特别感动的故事。我一个独立音乐人，没啥钱，但是也特别想送给大家节日礼物，准备了两把布鲁克吉他，在评论里随机抽两个",
//    "created_at": "2019-01-02T16:00:29.000Z",
//    "updated_at": "2022-03-09T08:42:10.000Z"
//},
//    "error": null,
//    "updateTime": 1704802379818
//}
data class WisdomResponse<T>(
    val code: Int = -1,
    val data: T?,
    val error: String? = "访问失败",
    val updateTime: Long = -1
)

val wisdomApi: WisdomApi by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(RetrofitUrl.windom)
//        .addConverterFactory(MoshiConverterFactory.create())//两种都可以返回Obj
        .addConverterFactory(GsonConverterFactory.create())//两种都可以Obj
//        .addConverterFactory(ScalarsConverterFactory.create())//直接返回基本类型
        .build()
    retrofit.create(WisdomApi::class.java)
}

data class WisdomData(
    val content: String? = null,
    val created_at: String? = null,
    val id: Int = -1,
    val name: String? = null,
    val origin: String? = null,
    val tag: String? = null,
    val updated_at: String? = null
)

interface WisdomApi {
    @GET("one")
    suspend fun getWisdom(): WisdomResponse<WisdomData>
}
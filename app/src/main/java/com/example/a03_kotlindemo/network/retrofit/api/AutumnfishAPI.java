package com.example.a03_kotlindemo.network.retrofit.api;

import com.example.a03_kotlindemo.network.retrofit.BaseResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AutumnfishAPI {
    String baseUrl = RetrofitUrl.autumnfish;

    @GET("joke")
    Observable<String> getjoke(@Query("num") String num);

    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<String>> postRegister(@Field("username") String username);
}
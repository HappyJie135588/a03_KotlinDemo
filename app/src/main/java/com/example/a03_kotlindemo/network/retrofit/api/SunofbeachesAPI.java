package com.example.a03_kotlindemo.network.retrofit.api;

import com.example.a03_kotlindemo.network.model.PriseArticleData;
import com.example.a03_kotlindemo.network.retrofit.BaseResponse;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SunofbeachesAPI {
    String baseUrl = RetrofitUrl.sunofbeaches;

    @POST("ast/prise/article")
    Observable<BaseResponse<String>> postJson(@Header("sob_token") String sob_token,
                                              @Body PriseArticleData priseArticleData);

    @POST("oss/image/mo_yu")
    @Multipart
    Observable<BaseResponse<String>> postFile(@Header("sob_token") String sob_token,
                                              @Part MultipartBody.Part part);
}
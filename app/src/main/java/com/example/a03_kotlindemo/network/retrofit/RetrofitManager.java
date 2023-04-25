package com.example.a03_kotlindemo.network.retrofit;

import com.example.a03_kotlindemo.network.retrofit.exception.ApiException;
import com.example.a03_kotlindemo.network.retrofit.api.AutumnfishAPI;
import com.example.a03_kotlindemo.network.retrofit.exception.CustomException;
import com.example.a03_kotlindemo.network.retrofit.api.SunofbeachesAPI;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitManager {
    protected AutumnfishAPI autumnfishAPI;
    protected AutumnfishAPI autumnfishAPIString;
    protected SunofbeachesAPI sunofbeachesAPI;

    protected RetrofitManager() {
        init();
    }

    protected static <T> ObservableTransformer<BaseResponse<T>, T> getData() {
        return upstream -> upstream
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends BaseResponse<T>>>() {
                    @Override
                    public ObservableSource<? extends BaseResponse<T>> apply(Throwable throwable) throws Throwable {
                        throwable.printStackTrace();
                        return Observable.error(CustomException.handleException(throwable));
                    }
                })
                .flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> baseResponse) throws Throwable {
                        if (baseResponse.success) {
                            if (baseResponse.data == null) {
                                baseResponse.data = (T) baseResponse.message;
                            }
                            return Observable.just(baseResponse.data);
                        } else {
                            return Observable.error(new ApiException(baseResponse.code, baseResponse.message));
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void init() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        autumnfishAPI = new Retrofit.Builder()
                .client(client)
                .baseUrl(AutumnfishAPI.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build().create(AutumnfishAPI.class);

        autumnfishAPIString = new Retrofit.Builder()
                .client(client)
                .baseUrl(AutumnfishAPI.baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build().create(AutumnfishAPI.class);

        sunofbeachesAPI = new Retrofit.Builder()
                .client(client)
                .baseUrl(SunofbeachesAPI.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build().create(SunofbeachesAPI.class);
    }
}
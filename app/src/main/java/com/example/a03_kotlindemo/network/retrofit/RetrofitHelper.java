package com.example.a03_kotlindemo.network.retrofit;

import com.example.a03_kotlindemo.network.model.PriseArticleData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;

public class RetrofitHelper extends RetrofitManager {

    public static Gson gson;
    private String sob_token;

    private RetrofitHelper() {
        //gson可以设置日期格式
        gson = new GsonBuilder().setDateFormat("YYYY-MM-DD hh:mm:ss").create();
        sob_token = "a_495fb9134b6d482a0fc010849def0e7f";
    }

    public static final RetrofitHelper getInstance() {
        return RetrofitHelperHolder.INSTANCE;
    }

    private static class RetrofitHelperHolder {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    /**
     * get请求
     *
     * @param num 参数
     */
    public Observable<String> getJoke(int num) {
        return autumnfishAPIString.getjoke(num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 上传Form
     *
     * @param username 参数
     */
    public Observable<String> postRegister(String username) {
        return autumnfishAPI.postRegister(username)
                .compose(getData());
    }

    /**
     * 上传Json
     *
     * @param priseArticleData 对象
     */
    public Observable<String> postJson(PriseArticleData priseArticleData) {
        return sunofbeachesAPI.postJson(sob_token, priseArticleData)
                .compose(getData());
    }

    /**
     * 上传文件
     *
     * @param fileName 文件名
     */
    public Observable<String> postFile(String fileName) {
        File file = new File(fileName);
        MultipartBody.Part part = MultipartBody.Part.createFormData(
                "image", file.getName(),
                MultipartBody.create(MediaType.parse("image/png"), file));
        return sunofbeachesAPI.postFile(sob_token, part)
                .compose(getData());
    }
}
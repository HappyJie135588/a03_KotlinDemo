package com.example.a03_kotlindemo.network.okhttp;

import com.example.a03_kotlindemo.network.model.PriseArticleData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;

/**
 * OkHttp帮助类，主要用于对外交互
 */
public class OkHelper extends OkManager {
    public static Gson gson;
    private String sob_token;

    private OkHelper() {
        super();
        //gson可以设置日期格式
        gson = new GsonBuilder().setDateFormat("YYYY-MM-DD hh:mm:ss").create();
        sob_token = "a_495fb9134b6d482a0fc010849def0e7f";
    }

    public static final OkHelper getInstance() {
        return OkHelperHolder.INSTANCE;
    }

    private static class OkHelperHolder {
        private static final OkHelper INSTANCE = new OkHelper();
    }

    /**
     * get请求
     *
     * @param num      参数
     * @param listener 回调
     */
    public void getJoke(String num, OkListener listener) {
        Properties params = new Properties();
        params.setProperty("num", num);
        get(OkUrl.getJoke, params, listener);
    }
    //get(@NonNull String url, @Nullable Properties query, @Nullable Map<String, String> header, OkListener listener)
    //post(@NonNull String url, @Nullable Properties query, @Nullable Map<String, String> header, @Nullable Properties params, @Nullable String json, @Nullable OkListener listener)

    /**
     * 上传Form
     *
     * @param username 参数
     * @param listener 回调
     */
    public void postRegister(String username, OkListener listener) {
        Properties params = new Properties();
        params.setProperty("username", username);
        postData(OkUrl.postData, params, listener);
    }

    /**
     * 上传Json
     *
     * @param priseArticleData 对象
     * @param listener         回调
     */
    public void postPrise(PriseArticleData priseArticleData, OkListener listener) {
        HashMap map = new HashMap();
        map.put("sob_token", sob_token);
        postRaw(OkUrl.postJson, map, gson.toJson(priseArticleData), listener);
    }

    /**
     * 上传文件
     *
     * @param fileName 文件名
     * @param listener 回调
     */
    public void postFile(String fileName, OkListener listener) {
        postFile(OkUrl.postFile, sob_token, "image", new File(fileName), listener);
    }
}

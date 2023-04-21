package com.example.a03_kotlindemo.network.okhttp.intercept;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 写着玩儿的自定义Log拦截器
 */
public class LogIntercept implements Interceptor {

    private static String TAG = "LogIntercept";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long curtime = System.currentTimeMillis();
        Log.d(TAG, "intercept: request=" + request.toString());
        Response response = chain.proceed(request);
        Log.d(TAG, "intercept: Response=" + response.toString());
        Log.d(TAG, "intercept: 耗时=" + (System.currentTimeMillis() - curtime) + "ms");
        return response;
    }
}

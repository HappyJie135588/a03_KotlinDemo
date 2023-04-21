package com.example.a03_kotlindemo.network.okhttp;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a03_kotlindemo.App;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttp管理类，不对外调用，谨慎修改，
 */
public class OkManager {
    private static String TAG = "OkManager";
    private OkHttpClient okHttpCliet;

    protected OkManager() {
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String s) {
                Log.v(TAG, s);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
        //cliet
        okHttpCliet = new OkHttpClient.Builder()
                //.addInterceptor(new LogIntercept())//自定义的Log拦截器
                .addInterceptor(interceptor)
                .build();
    }

    protected void get(@NonNull String url, OkListener listener) {
        get(url, null, null, listener);
    }

    protected void get(@NonNull String url, @Nullable Properties query, OkListener listener) {
        get(url, query, null, listener);
    }

    /**
     * get请求
     *
     * @param url      url
     * @param query    url参数
     * @param headers  请求头
     * @param listener 回调
     */
    protected void get(@NonNull String url, @Nullable Properties query, @Nullable Map<String, String> headers, OkListener listener) {
        //query
        String realUrl = url + getParams(query);
        //request
        Request.Builder requestBuilder = new Request.Builder()
                .url(realUrl)
                .get();//默认就是get
        //headers
        if (headers != null && !headers.isEmpty()) {
            requestBuilder.headers(Headers.of(headers));
        }
        //doRequest
        doRequest(requestBuilder.build(), listener);
    }

    protected void postData(@NonNull String url, @Nullable Properties data, @Nullable OkListener listener) {
        post(url, null, null, data, null, listener);
    }

    protected void postData(@NonNull String url, @Nullable Map<String, String> headers, @Nullable Properties data, @Nullable OkListener listener) {
        post(url, null, headers, data, null, listener);
    }

    protected void postData(@NonNull String url, @Nullable Properties query, @Nullable Properties data, @Nullable OkListener listener) {
        post(url, query, null, data, null, listener);
    }

    protected void postData(@NonNull String url, @Nullable Properties query, @Nullable Map<String, String> headers, @Nullable Properties data, @Nullable OkListener listener) {
        post(url, query, headers, data, null, listener);
    }

    protected void postRaw(@NonNull String url, @Nullable String raw, @Nullable OkListener listener) {
        post(url, null, null, null, raw, listener);
    }

    protected void postRaw(@NonNull String url, @Nullable Map<String, String> headers, @Nullable String raw, @Nullable OkListener listener) {
        post(url, null, headers, null, raw, listener);
    }

    protected void postRaw(@NonNull String url, @Nullable Properties query, @Nullable String raw, @Nullable OkListener listener) {
        post(url, query, null, null, raw, listener);
    }

    protected void postRaw(@NonNull String url, @Nullable Properties query, @Nullable Map<String, String> headers, @Nullable String raw, @Nullable OkListener listener) {
        post(url, query, headers, null, raw, listener);
    }

    /**
     * post请求
     *
     * @param url      url
     * @param query    url参数
     * @param headers  请求头
     * @param data     表单参数
     * @param raw      json
     * @param listener 回调
     */
    protected void post(@NonNull String url, @Nullable Properties query, @Nullable Map<String, String> headers, @Nullable Properties data, @Nullable String raw, @Nullable OkListener listener) {
        //query
        String realUrl = url + getParams(query);
        //params
        FormBody.Builder formBodyBuilder = null;
        if (data != null && !data.isEmpty()) {
            formBodyBuilder = new FormBody.Builder();
            for (String name : data.stringPropertyNames()) {
                formBodyBuilder.add(name, data.getProperty(name));
            }
        }
        //raw
        RequestBody requestBody = null;
        if (!TextUtils.isEmpty(raw)) {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), raw);
        }
        //request
        Request.Builder request = new Request.Builder()
                .url(realUrl);
        //body
        if (formBodyBuilder != null) {
            request.post(formBodyBuilder.build());

        } else if (requestBody != null) {
            request.post(requestBody);
        }
        //headers
        if (headers != null && !headers.isEmpty()) {
            request.headers(Headers.of(headers));
        }
        //doRequest
        doRequest(request.build(), listener);
    }

    /**
     * 上传单个文件的简单实现，如有需求，可根据需求实现
     *
     * @param url      url
     * @param name     接受文件的参数
     * @param file     文件
     * @param listener 回调
     */
    protected void postFile(@NonNull String url, String sob_token, String name, File file, OkListener listener) {
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单形式
//                .addFormDataPart("image", fileName, MultipartBody.create(MediaType.parse("multipart/form-data"),new File(fileName)))
                .addFormDataPart("image", file.getName(), MultipartBody.create(MediaType.parse("image/png"), file))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("sob_token", sob_token)
                .post(requestBody)
                .build();
        doRequest(request, listener);
    }

    /**
     * OkHttp统一请求方法
     *
     * @param request  request
     * @param listener 回调
     */
    private void doRequest(Request request, OkListener listener) {
        okHttpCliet.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    App.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError(e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
//                Log.d(TAG, "onResponse: reult=" + result);
                if (listener != null) {
                    App.post(new Runnable() {
                        @Override
                        public void run() {
                            //可以在此添加统一的错误处理
                            /*try {
                                JSONObject jsonObject = new JSONObject(str);
                                if (jsonObject.has("code")) {
                                    baseResponse.code = jsonObject.getInt("code");
                                }
                                if (jsonObject.has("msg")) {
                                    baseResponse.msg = jsonObject.getString("msg");
                                }
                                if (jsonObject.has("x_request_id")) {
                                    baseResponse.msg = jsonObject.getString("x_request_id");
                                }
                                if (jsonObject.has("data")) {
                                    baseResponse.data = jsonObject.getString("data");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (listener != null) {
                                    listener.onError(new Throwable(e));
                                }
                            }

                            if (baseResponse.code != 0) {
                                if (listener != null) {
                                    listener.onError(new Throwable("code:" + baseResponse.code + " msg" + baseResponse.msg + " x_request_id：" + baseResponse.x_request_id));
                                }
                            } else {
                                if (listener != null) {
                                    listener.onSuccess(baseResponse.data.toString());
                                }
                            }*/
                            listener.onSuccess(result);
                        }
                    });
                }
            }
        });
    }

    /**
     * 拼接url参数的工具方法
     *
     * @param params
     * @return
     */
    private String getParams(@Nullable Properties params) {
        if (params != null && params.size() != 0) {
            StringBuffer sb = new StringBuffer("?");
            for (String key : params.stringPropertyNames()) {
                String value = params.getProperty(key);
                if (!TextUtils.isEmpty(value)) {
                    sb.append(key);
                    sb.append("=");
                    sb.append(value);
                    sb.append("&");
                }
            }
            if (sb.toString().endsWith("&")) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        } else {
            return "";
        }
    }
}

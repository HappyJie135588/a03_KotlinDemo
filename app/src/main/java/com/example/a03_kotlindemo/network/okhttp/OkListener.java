package com.example.a03_kotlindemo.network.okhttp;

public abstract class OkListener {
    protected abstract void onSuccess(String str);

    void onError(Throwable throwable) {
        throwable.printStackTrace();
    }
}

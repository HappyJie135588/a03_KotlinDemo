package com.example.a03_kotlindemo.network.retrofit.exception;

import com.example.a03_kotlindemo.network.retrofit.exception.ApiException;

import io.reactivex.rxjava3.functions.Consumer;

public abstract class ErrorConsumer implements Consumer<Throwable> {
    @Override
    public void accept(Throwable throwable) throws Throwable {
        error((ApiException) throwable);
    }

    protected abstract void error(ApiException ex);
}
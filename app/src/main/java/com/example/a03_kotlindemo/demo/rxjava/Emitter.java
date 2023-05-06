package com.example.a03_kotlindemo.demo.rxjava;

public interface Emitter<T> {

    void onNext(T t);

    void onError(Throwable throwable);

    void onComplete();

}
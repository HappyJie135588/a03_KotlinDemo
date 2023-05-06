package com.example.a03_kotlindemo.demo.rxjava;

public interface Observer<T> {

    void onSubscribe();

    void onNext(T t);

    void onError(Throwable throwable);

    void onComplete();
}
package com.example.a03_kotlindemo.demo.rxjava;

/**
 * 观察者接口，提供处理事件的回调方法
 *
 * @param <T>
 */
public interface Observer<T> {

    void onSubscribe();

    void onNext(T t);

    void onError(Throwable throwable);

    void onComplete();
}
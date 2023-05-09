package com.example.a03_kotlindemo.demo.rxjava;

/**
 * 事件发射器接口，提供发送事件的方法
 *
 * @param <T>
 */
public interface Emitter<T> {

    void onNext(T t);

    void onError(Throwable throwable);

    void onComplete();

}
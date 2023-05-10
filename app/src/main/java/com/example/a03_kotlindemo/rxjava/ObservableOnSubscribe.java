package com.example.a03_kotlindemo.rxjava;

/**
 * 被观察者与事件解耦的接口
 *
 * @param <T>
 */
public interface ObservableOnSubscribe<T> {

    void subscribe(Emitter<T> emitter);
}
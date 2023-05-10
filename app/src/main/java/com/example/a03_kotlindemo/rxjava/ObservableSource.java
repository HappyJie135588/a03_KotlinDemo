package com.example.a03_kotlindemo.rxjava;

/**
 * 被观察者的顶层接口，提供订阅方法
 */
public interface ObservableSource<T> {
    /**
     * addObserver
     *
     * @param observer
     */
    void subscribe(Observer<T> observer);
}
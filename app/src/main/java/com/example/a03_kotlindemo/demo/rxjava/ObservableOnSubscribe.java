package com.example.a03_kotlindemo.demo.rxjava;

public interface ObservableOnSubscribe<T> {

    void subscribe(Emitter<T> emitter);
}
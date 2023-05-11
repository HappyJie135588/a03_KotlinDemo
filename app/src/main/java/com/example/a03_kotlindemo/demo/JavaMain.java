package com.example.a03_kotlindemo.demo;

import com.example.a03_kotlindemo.rxjava.rxbus.RxBus;

import io.reactivex.rxjava3.functions.Consumer;

public class JavaMain {
    public static void main(String[] args) {
        System.out.println("JavaDemo");
        RxBus.get().post("aaaa");
        RxBus.get().toObservable(String.class).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                System.out.println(s);
            }
        });
        RxBus.get().post("bbbb");
    }
}

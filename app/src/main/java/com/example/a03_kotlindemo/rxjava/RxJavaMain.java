package com.example.a03_kotlindemo.rxjava;

import com.example.a03_kotlindemo.rxjava.map.Function;

public class RxJavaMain {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(Emitter<String> emitter) {
                emitter.onNext("123");
                emitter.onNext("456");
                emitter.onComplete();

            }
        }).flatMap(new Function<String, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(String s) {
                return Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(Emitter<Integer> emitter) {
                        emitter.onNext(Integer.parseInt(s));
                    }
                });
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return integer + "...";
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe() {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext:" + s);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onNext:" + throwable);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
}
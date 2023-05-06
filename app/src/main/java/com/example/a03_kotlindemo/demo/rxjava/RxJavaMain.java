package com.example.a03_kotlindemo.demo.rxjava;

public class RxJavaMain {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(Emitter<String> emitter) {
                emitter.onNext("aaa");
                emitter.onNext("bbb");
                emitter.onError(new Throwable());
                emitter.onComplete();

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
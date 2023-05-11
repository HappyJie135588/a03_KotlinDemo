package com.example.a03_kotlindemo.rxjava.rxbus;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class RxBus {
    private final Subject mBus;

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

    private RxBus() {
        //toSerialized：支持线程安全
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus get() {
        return Holder.BUS;
    }

    public void post(Object event) {
        mBus.onNext(event);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }
}

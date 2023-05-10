package com.example.a03_kotlindemo.rxjava;

import com.example.a03_kotlindemo.rxjava.map.ObservableFlatMap;
import com.example.a03_kotlindemo.rxjava.scheduler.ObservableObserveOn;
import com.example.a03_kotlindemo.rxjava.scheduler.ObservableSubscribeOn;
import com.example.a03_kotlindemo.rxjava.scheduler.Scheduler;
import com.example.a03_kotlindemo.rxjava.map.Function;
import com.example.a03_kotlindemo.rxjava.map.ObservableMap;

/**
 * 被观察者的核心抽象类
 * 实现ObservableSource接口
 * 提供实际订阅的抽象方法
 */
public abstract class Observable<T> implements ObservableSource<T> {
    @Override
    public void subscribe(Observer<T> observer) {

        //和谁建立订阅？
        //怎么建立订阅？
        //为了保证拓展性，交给具体的开发人员实现，这里提供一个抽象的方法
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<T> observer);

    public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
        return new ObservableCreate<>(source);
    }

    public <R> ObservableMap<T, R> map(Function<T, R> function) {
        return new ObservableMap<>(this, function);
    }

    public <R> ObservableFlatMap<T, R> flatMap(Function<T, ObservableSource<R>> function) {
        return new ObservableFlatMap<>(this, function);
    }

    public ObservableSubscribeOn<T> subscribeOn(Scheduler scheduler) {
        return new ObservableSubscribeOn<>(this, scheduler);
    }

    public ObservableObserveOn<T> observeOn(Scheduler scheduler) {
        return observeOn( scheduler,false);
    }

    /**
     *
     * @param scheduler
     * @param delayError 延迟执行错误
     * @return
     */
    public ObservableObserveOn<T> observeOn(Scheduler scheduler,boolean delayError) {
        return new ObservableObserveOn<>(this, scheduler,delayError);
    }
}
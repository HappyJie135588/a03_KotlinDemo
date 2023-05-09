package com.example.a03_kotlindemo.demo.rxjava.map;

import com.example.a03_kotlindemo.demo.rxjava.Observable;
import com.example.a03_kotlindemo.demo.rxjava.ObservableSource;

/**
 * 装饰器模式，避免太多子类
 *
 * @param <T>
 * @param <U>
 */
public abstract class AbstractObservableWithUpStream<T, U> extends Observable<U> {

    protected final ObservableSource source;

    public AbstractObservableWithUpStream(ObservableSource source) {
        this.source = source;
    }
}

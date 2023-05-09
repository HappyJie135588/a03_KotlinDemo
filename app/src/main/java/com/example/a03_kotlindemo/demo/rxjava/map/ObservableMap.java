package com.example.a03_kotlindemo.demo.rxjava.map;

import com.example.a03_kotlindemo.demo.rxjava.ObservableSource;
import com.example.a03_kotlindemo.demo.rxjava.Observer;

/**
 * 具体的map转换被观察者实现类，持有ObservableOnSubscribe接口的引用
 *
 * @param <T>
 * @param <U>
 */
public class ObservableMap<T, U> extends AbstractObservableWithUpStream<T, U> {
    Function<T, U> function;

    public ObservableMap(ObservableSource source, Function<T, U> function) {
        super(source);
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer<U> observer) {
        source.subscribe(new MapObserver<T, U>(observer, function));
    }

    /**
     * 具体观察者的实现类
     *
     * @param <T>
     * @param <U>
     */
    static class MapObserver<T, U> implements Observer<T> {
        final Observer<U> downStream;
        final Function<T, U> mapper;

        public MapObserver(Observer<U> downStream, Function<T, U> mapper) {
            this.downStream = downStream;
            this.mapper = mapper;
        }

        @Override
        public void onSubscribe() {
            downStream.onSubscribe();
        }

        @Override
        public void onNext(T t) {
            //map操作符的核心
            U u = mapper.apply(t);
            downStream.onNext(u);

        }

        @Override
        public void onError(Throwable throwable) {
            downStream.onError(throwable);
        }

        @Override
        public void onComplete() {
            downStream.onComplete();
        }
    }
}























package com.example.a03_kotlindemo.rxjava.map;

import com.example.a03_kotlindemo.rxjava.ObservableSource;
import com.example.a03_kotlindemo.rxjava.Observer;

/**
 * 具体的flatMap转换被观察者实现类，持有ObservableOnSubscribe接口的引用
 *
 * @param <T>
 * @param <U>
 */
public class ObservableFlatMap<T, U> extends AbstractObservableWithUpStream<T, U> {
    Function<T, ObservableSource<U>> function;

    public ObservableFlatMap(ObservableSource source, Function<T, ObservableSource<U>> function) {
        super(source);
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer<U> observer) {
        source.subscribe(new MergeObserver<>(observer, function));
    }

    /**
     * 具体观察者的实现类
     *
     * @param <T>
     * @param <U>
     */
    static class MergeObserver<T, U> implements Observer<T> {
        final Observer<U> downStream;
        final Function<T, ObservableSource<U>> mapper;

        public MergeObserver(Observer<U> downStream, Function<T, ObservableSource<U>> mapper) {
            this.downStream = downStream;
            this.mapper = mapper;
        }

        @Override
        public void onSubscribe() {
            downStream.onSubscribe();
        }

        @Override
        public void onNext(T t) {
            ObservableSource<U> observable = mapper.apply(t);
            observable.subscribe(new Observer<U>() {
                @Override
                public void onSubscribe() {

                }

                @Override
                public void onNext(U u) {
                    downStream.onNext(u);
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onComplete() {

                }
            });
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























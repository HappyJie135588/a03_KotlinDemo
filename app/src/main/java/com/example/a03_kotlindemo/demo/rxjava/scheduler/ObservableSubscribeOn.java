package com.example.a03_kotlindemo.demo.rxjava.scheduler;

import com.example.a03_kotlindemo.demo.rxjava.ObservableSource;
import com.example.a03_kotlindemo.demo.rxjava.Observer;
import com.example.a03_kotlindemo.demo.rxjava.map.AbstractObservableWithUpStream;

public class ObservableSubscribeOn<T> extends AbstractObservableWithUpStream<T, T> {

    final Scheduler scheduler;

    public ObservableSubscribeOn(ObservableSource source, Scheduler scheduler) {
        super(source);
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        observer.onSubscribe();
        Scheduler.Worker worker = scheduler.createWorker();
        worker.schedule(new SubscribeTask(new SubscribeOnObserver<>(observer)));
    }

    static class SubscribeOnObserver<T> implements Observer<T> {
        final Observer downStream;

        public SubscribeOnObserver(Observer downStream) {
            this.downStream = downStream;
        }

        @Override
        public void onSubscribe() {
        }

        @Override
        public void onNext(T t) {
            downStream.onNext(t);
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


    final class SubscribeTask<T> implements Runnable {

        final SubscribeOnObserver<T> parent;

        public SubscribeTask(SubscribeOnObserver<T> parent) {
            this.parent = parent;
        }

        @Override
        public void run() {
            source.subscribe(parent);
        }
    }
}






















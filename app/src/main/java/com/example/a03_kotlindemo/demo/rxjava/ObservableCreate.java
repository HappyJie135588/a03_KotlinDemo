package com.example.a03_kotlindemo.demo.rxjava;

/**
 * 具体的创建被观察者实现类，持有ObservableOnSubscribe接口的引用
 *
 * @param <T>
 */
public class ObservableCreate<T> extends Observable<T> {

    final ObservableOnSubscribe<T> source;

    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        observer.onSubscribe();
        CreateEmitter<T> emitter = new CreateEmitter<>(observer);
        source.subscribe(emitter);
    }

    /**
     * 事件发射器具体实现，持有观察者的引用
     *
     * @param <T>
     */
    static class CreateEmitter<T> implements Emitter<T> {
        Observer<T> observer;
        boolean done;

        public CreateEmitter(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T t) {
            if (done) return;
            observer.onNext(t);
        }

        @Override
        public void onError(Throwable throwable) {
            if (done) return;
            observer.onError(throwable);
            done = true;
        }

        @Override
        public void onComplete() {
            if (done) return;
            observer.onComplete();
            done = true;
        }
    }
}
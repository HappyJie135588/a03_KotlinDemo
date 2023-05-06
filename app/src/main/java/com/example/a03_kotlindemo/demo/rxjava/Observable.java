package com.example.a03_kotlindemo.demo.rxjava;

/**
 * 被观察者的核心抽象类；
 * 也是使用框架的入口
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
}
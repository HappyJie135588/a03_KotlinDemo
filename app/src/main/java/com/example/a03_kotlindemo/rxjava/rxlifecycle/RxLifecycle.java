package com.example.a03_kotlindemo.rxjava.rxlifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;


public class RxLifecycle<T> implements LifecycleObserver, ObservableTransformer<T,T> {
    private static final String TAG = "RxLifecycle";
    final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(){
        Log.i(TAG,"onDestroy: 取消订阅");
        if(!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                compositeDisposable.add(disposable);
            }
        });
    }

    public static <T> RxLifecycle<T> bindRxLifecycle(LifecycleOwner lifecycleOwner){
        RxLifecycle<T> lifecycle = new RxLifecycle<>();
        lifecycleOwner.getLifecycle().addObserver(lifecycle);
        return lifecycle;
    }
}

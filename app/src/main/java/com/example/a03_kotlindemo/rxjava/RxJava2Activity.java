package com.example.a03_kotlindemo.rxjava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.databinding.ActivityRxJava2Binding;
import com.example.a03_kotlindemo.rxjava.rxlifecycle.RxLifecycle;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxJava2Activity extends AppCompatActivity {
    private static final String TAG = "RxJava2Activity";
    private ActivityRxJava2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRxJava2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                        Log.d(TAG, "create:" + Thread.currentThread().getName());
                        emitter.onNext("123");
                    }
                })
                .compose(RxLifecycle.bindRxLifecycle(this))//绑定自定义的RxLifecycle，在页面销毁时取消订阅
                .subscribeOn(Schedulers.io())
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Throwable {
                        Log.d(TAG, "map1:" + Thread.currentThread().getName());
                        return o;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Throwable {
                        Log.d(TAG, "map2:" + Thread.currentThread().getName());
                        return o;
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Throwable {
                        Log.d(TAG, "map3:" + Thread.currentThread().getName());
                        return o;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Throwable {
                        Log.d(TAG, "map4:" + Thread.currentThread().getName());
                        return o;
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Throwable {
                        Log.d(TAG, "accept:" + Thread.currentThread().getName());
                        Log.d(TAG, "accept: " + o);
                    }
                });
    }
}
package com.example.a03_kotlindemo.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.databinding.ActivityRxJava2Binding;
import com.example.a03_kotlindemo.rxjava.rxbus.RxBus;
import com.example.a03_kotlindemo.rxjava.rxlifecycle.RxLifecycle;
import com.example.a03_kotlindemo.utils.ToastUtils;
import com.jakewharton.rxbinding4.view.RxView;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;

public class RxJava2Activity extends AppCompatActivity {
    private static final String TAG = "RxJava2Activity";
    private ActivityRxJava2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRxJava2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //线程切换测试
        binding.btnRxThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new ObservableOnSubscribe<Object>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                                Log.d(TAG, "create:" + Thread.currentThread().getName());
                                emitter.onNext("123");
                            }
                        })
                        .compose(RxLifecycle.bindRxLifecycle(RxJava2Activity.this))//绑定自定义的RxLifecycle，在页面销毁时取消订阅
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
        });

        //RxPermissions权限申请
        binding.btnRxPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RxPermissions(RxJava2Activity.this)
                        .request("android.permission.CAMERA")
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Throwable {
                                if (aBoolean) {
                                    ToastUtils.show("权限获取成功");
                                } else {
                                    ToastUtils.show("权限获取失败");
                                }
                            }
                        });
            }
        });

        //RxBus发送事件
        binding.btnRxBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.get().post("aaaa");
            }
        });
        RxBus.get().toObservable(String.class).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                System.out.println(s);
            }
        });

        //RxBinding防止多次点击
        RxView.clicks(binding.btnRxBinding)
                .throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Throwable {
                        Log.d(TAG, "accept: 每隔5秒只能点击1次");
                        ToastUtils.show("每隔5秒只能点击1次");
                    }
                });
    }
}
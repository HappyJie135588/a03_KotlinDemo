package com.example.a03_kotlindemo.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.databinding.ActivityRxjavaBinding;
import com.example.a03_kotlindemo.rxjava.map.Function;
import com.example.a03_kotlindemo.rxjava.scheduler.Schedulers;

public class RxjavaActivity extends AppCompatActivity {
    private static final String TAG = "RxjavaActivity";
    private ActivityRxjavaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRxjavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initRxjava();
    }

    private void initRxjava() {
        binding.btnRxjavaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RxjavaActivity.this, RxJava2Activity.class));
            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(Emitter<Integer> emitter) {
                        Log.d(TAG, "create: " + Thread.currentThread().getName());
                        emitter.onNext(123);
                        emitter.onNext(456);
                        emitter.onError(new Throwable());
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.mainThread(), true)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer s) {
                        Log.d(TAG, "map: " + Thread.currentThread().getName());
                        return s.toString();
                    }
                }).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe() {
                        Log.d(TAG, "onSubscribe: " + Thread.currentThread().getName());
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(String o) {
                        Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                        Log.d(TAG, "onNext: " + o);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(TAG, "onError: " + Thread.currentThread().getName());
                        Log.d(TAG, "onError: " + throwable);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: " + Thread.currentThread().getName());
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
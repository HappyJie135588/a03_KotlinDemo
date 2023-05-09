package com.example.a03_kotlindemo.network;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.databinding.ActivityJavaMainBinding;
import com.example.a03_kotlindemo.demo.rxjava.Emitter;
import com.example.a03_kotlindemo.demo.rxjava.Observable;
import com.example.a03_kotlindemo.demo.rxjava.ObservableOnSubscribe;
import com.example.a03_kotlindemo.demo.rxjava.Observer;
import com.example.a03_kotlindemo.demo.rxjava.map.Function;
import com.example.a03_kotlindemo.demo.rxjava.scheduler.Schedulers;
import com.example.a03_kotlindemo.network.model.PriseArticleData;
import com.example.a03_kotlindemo.network.okhttp.OkHelper;
import com.example.a03_kotlindemo.network.okhttp.OkListener;
import com.example.a03_kotlindemo.network.retrofit.exception.ApiException;
import com.example.a03_kotlindemo.network.retrofit.exception.ErrorConsumer;
import com.example.a03_kotlindemo.network.retrofit.RetrofitHelper;

import io.reactivex.rxjava3.functions.Consumer;

public class JavaNetWorkActivity extends AppCompatActivity {

    private ActivityJavaMainBinding binding;
    private String TAG = "JavaMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJavaMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(Emitter<Integer> emitter) {
                        Log.d(TAG, "create: " + Thread.currentThread().getName());
                        emitter.onNext(123);
                    }
                }).subscribeOn(Schedulers.newThread())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer s) {
                        Log.d(TAG, "map: " + Thread.currentThread().getName());
                        return s.toString();
                    }
                }).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe() {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(String o) {
                        Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                        Log.d(TAG, "onNext: " + o);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(TAG, "onError: " + throwable);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void init() {
        binding.btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHelper.getInstance().getJoke("2", new OkListener() {
                    @Override
                    protected void onSuccess(String str) {
                        binding.tvGet.setText(str);
                    }
                });
            }
        });
        binding.btnPostForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHelper.getInstance().postRegister("HappyJie135588", new OkListener() {
                    @Override
                    protected void onSuccess(String str) {
                        binding.tvPostForm.setText(str);
                    }
                });

            }
        });
        binding.btnPostJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriseArticleData priseArticleData = new PriseArticleData("1403262826952323074", 2);
                OkHelper.getInstance().postPrise(priseArticleData, new OkListener() {
                    @Override
                    protected void onSuccess(String str) {
                        binding.tvPostJson.setText(str);
                    }
                });
            }
        });
        binding.btnPostFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHelper.getInstance().postFile("/sdcard/text1.png", new OkListener() {
                    @Override
                    protected void onSuccess(String str) {
                        binding.tvPostFile.setText(str);
                    }
                });
            }
        });

        binding.btnGet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitHelper.getInstance().getJoke("2").subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        binding.tvGet2.setText(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        throwable.printStackTrace();
                    }
                });
            }
        });
        binding.btnPostForm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitHelper.getInstance().postRegister("HappyJie1355882").subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        binding.tvPostForm2.setText(s);
                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException ex) {
                        binding.tvPostForm2.setText(ex.displayMessage);
                    }
                });

            }
        });
        binding.btnPostJson2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriseArticleData priseArticleData = new PriseArticleData("1403262826952323074", 2);
                RetrofitHelper.getInstance().postJson(priseArticleData).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        binding.tvPostJson2.setText(s);
                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException ex) {
                        binding.tvPostJson2.setText(ex.displayMessage);
                    }
                });
            }
        });

        binding.btnPostFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitHelper.getInstance().postFile("/sdcard/text1.png").subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        binding.tvPostFile2.setText(s);
                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException ex) {
                        binding.tvPostFile2.setText(ex.displayMessage);
                    }
                });
            }
        });

    }
}
package com.example.a03_kotlindemo.utils;

import android.widget.Toast;

import com.example.a03_kotlindemo.App;

public class ToastUtils {
    public static void show(String msg) {
        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
    }
}

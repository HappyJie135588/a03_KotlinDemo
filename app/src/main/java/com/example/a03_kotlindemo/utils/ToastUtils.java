package com.example.a03_kotlindemo.utils;

import android.widget.Toast;

import com.example.a03_kotlindemo.App;

public class ToastUtils {
    private static final boolean SHOW = false;

    public static void show(String msg) {
        if (SHOW) {
            Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}

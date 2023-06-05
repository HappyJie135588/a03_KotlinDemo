package com.example.a03_kotlindemo.utils;

import android.widget.Toast;

import com.example.a03_kotlindemo.App;
import com.example.a03_kotlindemo.Config;

public class ToastUtils {

    public static void show(String msg) {
        if (Config.IS_SHOW_TOAST) {
            Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}

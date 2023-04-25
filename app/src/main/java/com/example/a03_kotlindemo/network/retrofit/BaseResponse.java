package com.example.a03_kotlindemo.network.retrofit;

public class BaseResponse<T> {
    //{"success":true,"code":10000,"message":"感谢您的打赏！","data":null}
    public boolean success;
    public int code;
    public String message;
    public T data;
}
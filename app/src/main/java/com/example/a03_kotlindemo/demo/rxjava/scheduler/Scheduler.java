package com.example.a03_kotlindemo.demo.rxjava.scheduler;

public abstract class Scheduler {
    public abstract Worker createWorker();

    public interface Worker{
        void schedule(Runnable runnable);
    }

}

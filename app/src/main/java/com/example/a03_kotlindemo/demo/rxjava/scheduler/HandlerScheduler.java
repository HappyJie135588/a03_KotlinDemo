package com.example.a03_kotlindemo.demo.rxjava.scheduler;

import android.os.Handler;

public class HandlerScheduler extends Scheduler {
    final Handler handler;

    public HandlerScheduler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public Worker createWorker() {
        return new HandlerWorker(handler);
    }

    static final class HandlerWorker implements Worker {

        final Handler mapper;

        public HandlerWorker(Handler mapper) {
            this.mapper = mapper;
        }

        @Override
        public void schedule(Runnable runnable) {
            mapper.post(runnable);
        }
    }
}


















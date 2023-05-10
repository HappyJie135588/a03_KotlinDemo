package com.example.a03_kotlindemo.rxjava.scheduler;

import com.example.a03_kotlindemo.rxjava.ObservableSource;
import com.example.a03_kotlindemo.rxjava.Observer;
import com.example.a03_kotlindemo.rxjava.map.AbstractObservableWithUpStream;

import java.util.ArrayDeque;
import java.util.Deque;

public class ObservableObserveOn<T> extends AbstractObservableWithUpStream<T, T> {
    final Scheduler scheduler;
    final boolean delayError;

    public ObservableObserveOn(ObservableSource source, Scheduler scheduler,boolean delayError) {
        super(source);
        this.scheduler = scheduler;
        this.delayError = delayError;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        Scheduler.Worker worker = scheduler.createWorker();
        source.subscribe(new ObserveOnObserver(observer, worker,delayError));
    }

    static final class ObserveOnObserver<T> implements Observer<T>, Runnable {

        final Observer<T> downSteam;
        final Scheduler.Worker worker;
        final boolean delayError;
        final Deque<T> queue;

        volatile boolean done;//是否结束
        volatile Throwable error;//异常
        volatile boolean over;

        public ObserveOnObserver(Observer<T> downSteam, Scheduler.Worker worker,boolean delayError) {
            this.downSteam = downSteam;
            this.worker = worker;
            this.queue = new ArrayDeque<>();
            this.delayError = delayError;
        }

        @Override
        public void onSubscribe() {
            downSteam.onSubscribe();
        }

        @Override
        public void onNext(T t) {
            if(done){
                return;
            }
            queue.offer(t);//把事件加入队列，offer不抛异常，只会返回false
            schedule();
        }

        @Override
        public void onError(Throwable throwable) {
            if(done){
                return;
            }
            error = throwable;
            done = true;
            schedule();
        }

        @Override
        public void onComplete() {
            if(done){
                return;
            }
            done = true;
            schedule();
        }

        private void schedule() {
            worker.schedule(this);
        }

        @Override
        public void run() {
            drainNormal();
        }

        /**
         * 从队列中排放事件并处理
         */
        private void drainNormal() {
            final Deque<T> q = queue;
            final Observer<T> a = downSteam;
            while (true) {
                boolean d = done;
                T t = q.poll();//取出数据，没有数据的时候不会抛异常，返回null
                boolean empty = t == null;
                if (checkTerminated(d, empty, a)) {
                    return;
                }
                if(empty){
                    break;
                }
                a.onNext(t);
            }

        }

        private boolean checkTerminated(boolean d, boolean empty, Observer<T> a) {
            if (over) {
                queue.clear();
                return true;
            }
            if (d) {
                Throwable e = error;
                if (delayError) {//错误延迟执行
                    if (empty) {
                        over = true;
                        if (e != null) {
                            a.onError(e);
                        } else {
                            a.onComplete();
                        }
                        return true;
                    }
                } else {//错误立即执行
                    if (e != null) {
                        over = true;
                        a.onError(error);
                        return true;
                    } else if (empty) {
                        over = true;
                        a.onComplete();
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
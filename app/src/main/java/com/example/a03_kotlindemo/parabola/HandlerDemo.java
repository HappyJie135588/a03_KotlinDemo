package com.example.a03_kotlindemo.parabola;

public class HandlerDemo extends Thread {
    Looper looper = new Looper();

    @Override
    public void run() {
        looper.loop();
    }
}

class Looper {
    private Runnable task;
    private boolean quit;

    synchronized void setTask(Runnable task) {
        this.task = task;
    }

    synchronized void quit() {
        quit = true;
    }

    public void loop() {
        while (!quit) {
            synchronized (this) {
                if (task != null) {
                    task.run();
                    task = null;
                }
            }
        }
    }
}
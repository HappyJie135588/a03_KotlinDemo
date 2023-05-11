package com.example.a03_kotlindemo.parabola;

public class HandlerMain {
    public static void main(String[] args) throws InterruptedException {
        HandlerDemo thread= new HandlerDemo();

        thread.start();

        Thread.sleep(500);

        thread.looper.setTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("hahahahhahhahaah");
            }
        });

        Thread.sleep(500);

        thread.looper.quit();
    }
}

package com.example.a03_kotlindemo.kt

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MainLifecycleObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(): Unit {
        println("MainLifecycleObserver:onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(): Unit {
        println("MainLifecycleObserver:onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(): Unit {
        println("MainLifecycleObserver:onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(): Unit {
        println("MainLifecycleObserver:onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(): Unit {
        println("MainLifecycleObserver:onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(): Unit {
        println("MainLifecycleObserver:onDestroy")
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
//    fun onAny(): Unit {
//        println("MainLifecycleObserver:onAny")
//    }
}
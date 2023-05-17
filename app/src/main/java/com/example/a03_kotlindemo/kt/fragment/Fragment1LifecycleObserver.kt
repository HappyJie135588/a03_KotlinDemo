package com.example.a03_kotlindemo.kt.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class Fragment1LifecycleObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(): Unit {
        println("Fragment1LifecycleObserver:onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(): Unit {
        println("Fragment1LifecycleObserver:onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(): Unit {
        println("Fragment1LifecycleObserver:onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(): Unit {
        println("Fragment1LifecycleObserver:onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(): Unit {
        println("Fragment1LifecycleObserver:onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(): Unit {
        println("Fragment1LifecycleObserver:onDestroy")
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
//    fun onAny(): Unit {
//        println("MainLifecycleObserver:onAny")
//    }
}
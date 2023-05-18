package com.example.a03_kotlindemo.demo

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * runBlocking把主线程变成一个协程,会阻塞主线程等待内部协程执行完毕
 */
fun main1() = runBlocking {
    var job1 = launch {
        delay(5000)
        println("job1 finished")
    }
    println("1111111111")
    job1.join()
    println("2222222222")
    var job2 = async {
        delay(5000)
        println("job2 finished")
        "job2 result"
    }
    println("33333333333")
    println(job2.await())
    println("44444444444")
}

fun mai1n() = runBlocking {
    val time = measureTimeMillis {
        val one = async { doOne() }
        val two = async { doTwo() }
        println("The result:${one.await() + two.await()}")
    }
    var xxx = withTimeout(1000) {
        "xxx"
    }
    withTimeoutOrNull(1000) {

    }
    var xx = coroutineScope {

    }
    var yy = CoroutineScope(Dispatchers.Default)
    yy.launch {

    }
    println(time)
}

suspend fun doOne(): Int {
    delay(2000)
    return 3
}

suspend fun doTwo(): Int {
    delay(2000)
    return 4
}

fun main() {
    runCatching {

    }.onFailure {

    }


}
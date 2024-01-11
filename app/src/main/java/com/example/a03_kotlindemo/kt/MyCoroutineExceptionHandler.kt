package com.example.a03_kotlindemo.kt

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * 全局异常处理器：不能对异常进行捕获，不能阻止程序崩溃，可用于程序调试与异常上报
 * 需要在mian/resources下面创建META-INF/services目录，并在其中创建一个名为kotlinx.coroutines.CoroutineExceptionHandler的文件，文件内容就是我们的全局异常处理器的全类名。
 */
class MyCoroutineExceptionHandler() : CoroutineExceptionHandler {
    companion object {
        private const val TAG = "MyCoroutineExceptionHandler"
    }

    override val key: CoroutineExceptionHandler.Key = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.d(TAG, "handleException: 异常没有被捕获$exception")
        Log.d(TAG, "handleException: 被抑制的异常${exception.suppressed.contentToString()}")
        exception.printStackTrace()
    }

}
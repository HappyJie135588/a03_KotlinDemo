package com.example.a03_kotlindemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a03_kotlindemo.databinding.ActivityKotlinMainBinding
import kotlinx.coroutines.*

class KotlinMainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityKotlinMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btn1.setOnClickListener {
            launch {
                var xxx = getXXX()
                println(Thread.currentThread().name + "OnClick()")
                binding.tv1.text = xxx
            }
        }
    }

    private suspend fun getXXX(): String {
        return withContext(Dispatchers.IO) {
            println(Thread.currentThread().name + "getXXX()")
            try {
                delay(10000)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            "sssss"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
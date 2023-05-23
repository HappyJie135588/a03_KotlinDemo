package com.example.a03_kotlindemo.car

import android.app.Presentation
import android.content.Context
import android.os.Bundle
import android.view.Display
import android.view.WindowManager
import com.example.a03_kotlindemo.databinding.PresentationSecondLayoutBinding

class SecondPresentation(outerContext: Context, display: Display) :
    Presentation(outerContext, display) {
    private lateinit var binding: PresentationSecondLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //必须要加这个Type不然会报异常
        window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        super.onCreate(savedInstanceState)
        binding = PresentationSecondLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
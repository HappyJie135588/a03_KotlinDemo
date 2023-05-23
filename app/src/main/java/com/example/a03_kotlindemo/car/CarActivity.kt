package com.example.a03_kotlindemo.car

import android.app.ActivityOptions
import android.app.Presentation
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.view.WindowManager.InvalidDisplayException
import androidx.appcompat.app.AppCompatActivity
import com.example.a03_kotlindemo.databinding.ActivityCarBinding

//{"", displayId 1, FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS, FLAG_TRUSTED, real 480 x 720, largest app 677 x 677, smallest app 480 x 480, appVsyncOff 0, presDeadline 16666666, mode 2, defaultMode 2, modes [{id=2, width=480, height=720, fps=60.0}],      hdrCapabilities null, minimalPostProcessingSupported false, rotation 0, state ON}, DisplayMetrics{density=0.8875, width=480, height=677, scaledDensity=0.8875, xdpi=142.0, ydpi=142.0}, isValid=true
//{"", displayId 2, FLAG_PRESENTATION,                   FLAG_TRUSTED, real 720 x 480, largest app 720 x 720, smallest app 480 x 480, appVsyncOff 0, presDeadline 33333332, mode 3, defaultMode 3, modes [{id=3, width=720, height=480, fps=60.000004}], hdrCapabilities null, minimalPostProcessingSupported false, rotation 0, state ON}, DisplayMetrics{density=0.8875, width=720, height=480, scaledDensity=0.8875, xdpi=142.0, ydpi=142.0}, isValid=true

class CarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.btnPresentation.setOnClickListener {
            showSecondPresentation()
        }
        binding.btnActivityOptions.setOnClickListener {
            showSecondActivityOptions()
        }
    }

    private fun showSecondPresentation() {
        val displayManagerService = getSystemService(DISPLAY_SERVICE) as DisplayManager
        val displays = displayManagerService.getDisplays()
        displays.forEach {
            println(it.toString())
        }
        if (displays.size > 1) {
            val presentation: Presentation = SecondPresentation(this, displays[1])
            try {
                presentation.show()
            } catch (e: InvalidDisplayException) {
                e.printStackTrace()
            }
        }
    }

    private fun showSecondActivityOptions() {
        val displayManagerService = getSystemService(DISPLAY_SERVICE) as DisplayManager
        val displays = displayManagerService.getDisplays()
        displays.forEach {
            println(it.toString())
        }
        if (displays.size > 1) {
            val options = ActivityOptions.makeBasic()
            options.launchDisplayId = displays[1].displayId
            val secondIntent = Intent(this, SecondDisplayActivity::class.java)
            secondIntent.addFlags(/*Intent.FLAG_ACTIVITY_MULTIPLE_TASK or*/ Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(secondIntent, options.toBundle())
        }
    }
}
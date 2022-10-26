package com.quizappdrink.drinkeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.quizappdrink.drinkeat.databinding.ActivityStartterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartterActivity : AppCompatActivity() {

    var counterForAlpha = 0.1f
    var diffForAlpha = 0.1f

    private var _binding: ActivityStartterBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStartterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initProgBar()
        cycleUpAndTextAlpha()
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_SCREEN_TIME_FOR_LOAD)
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initProgBar() {
        lifecycleScope.launch {
            for (progress in 1..100) {
                withContext(Dispatchers.Main) {
                    binding.progBarSplashScrn.progress = progress
                }
                delay(SPLASH_SCREEN_TIME_FOR_LOAD / 130)
            }
        }
    }

    companion object {
        private const val SPLASH_SCREEN_TIME_FOR_LOAD: Long = 2200
    }

    private fun cycleUpAndTextAlpha() {
        lifecycleScope.launch {
            while (true) {
                binding.tvLoading.alpha = counterForAlpha
                if (counterForAlpha >= 1f) {
                    diffForAlpha = -0.1f
                }
                if (counterForAlpha <= 0.1f) {
                    diffForAlpha = 0.1f
                }
                delay(60)
                counterForAlpha += diffForAlpha
            }
        }
    }
}
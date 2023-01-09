package com.project.speechtotext.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.project.speechtotext.*
import java.io.ByteArrayOutputStream
import java.util.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        moveForward()
    }

    private fun moveForward() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if(!DataPreference.getInstance(this@SplashScreenActivity).isOnBoardingScreenShowed()) {
                    Launcher.startFirstOnBoardingScreen(this@SplashScreenActivity)
                } else {
                    Launcher.startMainActivity(this@SplashScreenActivity)
                }
            }
        }, 3000)
    }
}
package com.project.speechtotext.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.project.speechtotext.Launcher
import com.project.speechtotext.R

class FirstOnBoardingScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_on_boarding_screen)
    }

    fun clickToStart(view: View) {
        Launcher.startSecondOnBoardingScreen(this)
    }
}
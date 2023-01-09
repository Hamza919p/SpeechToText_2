package com.project.speechtotext.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.project.speechtotext.*
import com.project.speechtotext.databinding.ActivitySecondOnBoardingScreenBinding

class SecondOnBoardingScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondOnBoardingScreenBinding
    private var customInterstitialAd: CustomInterstitialAd? = null
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_second_on_boarding_screen)
        customInterstitialAd = CustomInterstitialAd(this)
    }

    fun letsGetStarted(view: View) {
       initializeInterstitialAd()
    }

    private fun initializeInterstitialAd() {
        customInterstitialAd?.initialize()
        binding.btnGetStarted.isEnabled = false
        customInterstitialAd?.listener(object : InterstitialAdCallback {
            override fun onSuccess(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this@SecondOnBoardingScreenActivity)
                    customInterstitialAd?.mInterstitialAddCallbacks(mInterstitialAd!!)
                } else {
                    Log.d("InterstitialAdListener", "The interstitial ad wasn't ready yet.")
                }
            }

            override fun onDismissFullScreen() {
                binding.btnGetStarted.isEnabled = true
                DataPreference.getInstance(this@SecondOnBoardingScreenActivity).setOnBoardingScreenStatus(true)
                Launcher.startMainActivity(this@SecondOnBoardingScreenActivity)
            }

            override fun onFailure(interstitialAd: InterstitialAd?) {
                binding.btnGetStarted.isEnabled = true
                DataPreference.getInstance(this@SecondOnBoardingScreenActivity).setOnBoardingScreenStatus(true)
                Launcher.startMainActivity(this@SecondOnBoardingScreenActivity)
            }
        })
    }
}
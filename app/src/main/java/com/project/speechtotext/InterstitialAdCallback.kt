package com.project.speechtotext

import com.google.android.gms.ads.interstitial.InterstitialAd


interface InterstitialAdCallback {
    fun onSuccess(interstitialAd: InterstitialAd)
    fun onDismissFullScreen()
    fun onFailure(interstitialAd: InterstitialAd?)
}
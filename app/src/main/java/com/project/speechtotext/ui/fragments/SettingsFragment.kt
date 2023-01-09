package com.project.speechtotext.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.project.speechtotext.*
import com.project.speechtotext.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentSettingsBinding
    private var customInterstitialAd: CustomInterstitialAd? = null
    private var mInterstitialAd: InterstitialAd? = null

    companion object {
        private var instance: SettingsFragment?= null
        @Synchronized
        fun getInstance() : SettingsFragment? {
            if(instance == null) instance = SettingsFragment()
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_settings, container, false)
        initializeClickListeners()
        customInterstitialAd = CustomInterstitialAd(requireActivity())
        return binding.root
    }

    private fun initializeClickListeners() {
        binding.tvHelp.setOnClickListener(this)
        binding.tvPrivacyPolicy.setOnClickListener(this)
        binding.tvFeedback.setOnClickListener(this)
        binding.tvShareApp.setOnClickListener(this)
        binding.tvRateUs.setOnClickListener(this)
        binding.tvMoreApp.setOnClickListener(this)
        binding.tvAbout.setOnClickListener(this)
    }

    private fun initializeInterstitialAd() {
        customInterstitialAd?.initialize()
        binding.tvFeedback.isEnabled = false
        customInterstitialAd?.listener(object : InterstitialAdCallback {
            override fun onSuccess(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(requireActivity())
                    customInterstitialAd?.mInterstitialAddCallbacks(mInterstitialAd!!)
                } else {
                    Log.d("InterstitialAdListener", "The interstitial ad wasn't ready yet.")
                }
            }

            override fun onDismissFullScreen() {
                binding.tvFeedback.isEnabled = true
                Launcher.startFeedbackActivity(requireActivity())
            }

            override fun onFailure(interstitialAd: InterstitialAd?) {
                binding.tvFeedback.isEnabled = true
                Launcher.startFeedbackActivity(requireActivity())
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_help -> {

            }
            R.id.tv_privacy_policy -> {

            }
            R.id.tv_feedback -> {
               initializeInterstitialAd()
            }
            R.id.tv_share_app -> {

            }
            R.id.tv_rate_us -> {

            }
            R.id.tv_more_app -> {
                Launcher.navigateToPlayStore(requireActivity())
            }
            R.id.tv_about -> {

            }
        }
    }

}
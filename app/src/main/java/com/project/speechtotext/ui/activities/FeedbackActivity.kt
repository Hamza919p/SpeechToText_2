package com.project.speechtotext.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdRequest
import com.project.speechtotext.R
import com.project.speechtotext.databinding.ActivityFeedbackBinding

class FeedbackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_feedback)
        init()
    }

    private fun init() {
        val adRequest = AdRequest.Builder().build()
        binding.bannersAdd.loadAd(adRequest)
        binding.btnSend.setOnClickListener {
            if(binding.etEmail.text.isEmpty() || binding.etDesc.text.isEmpty())
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            else
            {
                binding.etEmail.setText("")
                binding.etDesc.setText("")
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
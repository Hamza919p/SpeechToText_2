package com.project.speechtotext.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdRequest
import com.project.speechtotext.Launcher
import com.project.speechtotext.R
import com.project.speechtotext.databinding.ActivityEditNoteBinding

class EditNoteActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditNoteBinding
    private var adRequest: AdRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_note)
        init()
    }

    private fun init() {
        val note = intent?.getStringExtra("note")
        adRequest = AdRequest.Builder().build()
        binding.bannersAdd.loadAd(adRequest!!)
        binding.etNote.setText(note)
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.ivShare.setOnClickListener(this)
    }

    fun onBackArrowClick(view: View) {
        this.finish()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_share -> {
                if(binding.etNote.text.isNotEmpty())
                    Launcher.shareText(binding.etNote.text.toString(), this)
            }
        }
    }
}
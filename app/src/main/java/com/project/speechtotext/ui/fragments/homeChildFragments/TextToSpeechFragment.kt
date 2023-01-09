package com.project.speechtotext.ui.fragments.homeChildFragments

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.project.speechtotext.Launcher
import com.project.speechtotext.OnClickListener
import com.project.speechtotext.R
import com.project.speechtotext.Utils
import com.project.speechtotext.adapters.HomeSavedNotesAdapter
import com.project.speechtotext.data.SpeechToTextDatabase
import com.project.speechtotext.data.SpeechToTextModel
import com.project.speechtotext.databinding.FragmentTextToSpeechBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TextToSpeechFragment private constructor(): Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentTextToSpeechBinding
    private var savedSpeeches: List<SpeechToTextModel> ?= null
    private lateinit var textToSpeech: TextToSpeech

    companion object {
        private var instance: TextToSpeechFragment?= null
        @Synchronized
        fun getInstance() : TextToSpeechFragment? {
            if(instance == null) instance = TextToSpeechFragment()
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_text_to_speech, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.tvDate.text = Utils.getDateAndTime()
        initializeTextToSpeech()
        initClickListeners()
        retrieveAllSpeechTexts()
    }

    private fun initializeTextToSpeech() {
        textToSpeech = TextToSpeech(requireContext()) { status ->
            if(status != TextToSpeech.ERROR) textToSpeech.language = Locale.UK
        }
    }

    private fun initClickListeners() {
        binding.tvShareText.setOnClickListener(this)
        binding.ivVolume.setOnClickListener(this)
        binding.tvConvertToSpeech.setOnClickListener(this)
    }

    private fun retrieveAllSpeechTexts() {
        GlobalScope.launch {
            savedSpeeches = SpeechToTextDatabase.getInstance(requireContext()).dao.getAllSpeechTexts()
            withContext(Dispatchers.Main) {
                initializeNotesAdapter(savedSpeeches!!)
            }
        }
    }

    private fun initializeNotesAdapter(speeches: List<SpeechToTextModel>) {
        val adapter = HomeSavedNotesAdapter(isSpeechToText = false, speeches)
        binding.rvNotes.adapter = adapter
        adapter.observerClickListener(object : OnClickListener{
            override fun onClick(position: Int) {
                binding.etText.setText(savedSpeeches!![position].text)
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_share_text -> {
                if(binding.etText.text.isNotEmpty())
                    Launcher.shareText(binding.etText.text.toString(), requireActivity())
            }

            R.id.tv_convert_to_speech -> {
                binding.ivVolume.isEnabled = false

                if(binding.etText.text.isNotEmpty()){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeech.speak(binding.etText.text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
                    } else {
                        textToSpeech.speak(binding.etText.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
                    }
                }
                binding.ivVolume.isEnabled = true
            }

            R.id.iv_volume -> {
                binding.ivVolume.isEnabled = false

                if(binding.etText.text.isNotEmpty()){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeech.speak(binding.etText.text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
                    } else {
                        textToSpeech.speak(binding.etText.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
                    }
                }
                binding.ivVolume.isEnabled = true
            }
        }
    }

}
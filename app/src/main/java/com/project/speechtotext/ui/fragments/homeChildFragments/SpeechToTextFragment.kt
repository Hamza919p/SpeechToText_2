package com.project.speechtotext.ui.fragments.homeChildFragments

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.project.speechtotext.*
import com.project.speechtotext.adapters.HomeSavedNotesAdapter
import com.project.speechtotext.data.SpeechToTextDatabase
import com.project.speechtotext.data.SpeechToTextModel
import com.project.speechtotext.databinding.FragmentSpeechToTextBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SpeechToTextFragment private constructor() : Fragment(), View.OnClickListener,
    RecognitionListener {
    private lateinit var binding: FragmentSpeechToTextBinding
    private lateinit var speech: SpeechRecognizer
    private var savedSpeeches: List<SpeechToTextModel> ?= null
    private var speechResult: ArrayList<String>? = null
    private var adapter: HomeSavedNotesAdapter? = null

    companion object {
        private var instance: SpeechToTextFragment? = null

        @Synchronized
        fun getInstance(): SpeechToTextFragment? {
            if (instance == null) instance = SpeechToTextFragment()
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_speech_to_text, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.tvDate.text = Utils.getDateAndTime()
        initializeClickListeners()
        initializeSpeechRecognizer()
        retrieveAllSpeechTexts()
        speechResult = ArrayList()
    }

    private fun retrieveAllSpeechTexts() {
        GlobalScope.launch {
            savedSpeeches = SpeechToTextDatabase.getInstance(requireContext()).dao.getAllSpeechTexts()
            withContext(Dispatchers.Main) {
                initializeSavedNotesAdapter(savedSpeeches!!)
            }
        }
    }

    private fun initializeSpeechRecognizer() {
        speech = SpeechRecognizer.createSpeechRecognizer(requireContext())
        speech.setRecognitionListener(this)
//        speechRec.launch(Launcher.startSpeechRecognizer())
    }

    private fun initializeClickListeners() {
        binding.ivMic.setOnClickListener(this)
        binding.tvSaveText.setOnClickListener(this)
        binding.tvCopyText.setOnClickListener(this)
        binding.tvShareText.setOnClickListener(this)
    }

    private fun startSpeechToText() {
        GlobalScope.launch(Dispatchers.Main) {
        speech.startListening(Launcher.startSpeechRecognizer())
        }
    }

    private fun stopSpeechToText() {
        speech.destroy()
        speech.stopListening()
    }

    private fun initializeSavedNotesAdapter(speeches: List<SpeechToTextModel>) {
        adapter = HomeSavedNotesAdapter(isSpeechToText = true, speeches)
        binding.rvNotes.adapter = adapter
        adapter?.observerClickListener(object : OnClickListener{
            override fun onClick(position: Int) {
                Launcher.startEditNoteActivity(requireActivity(), speeches[position].text)
            }
        })
    }

    private val recordAndStoreAudioPerm = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            when {
                permissions.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false) -> {
                }
                permissions.getOrDefault(Manifest.permission.RECORD_AUDIO, false) -> {
                }
            }
            playPauseSpeechToText()
        }
    }

    private fun playPauseSpeechToText() {
        if (binding.ivMic.tag == "start") {
            initializeSpeechRecognizer()
            Toast.makeText(requireContext(), "Started", Toast.LENGTH_SHORT).show()
            binding.tvPressMic.text = requireActivity().getString(R.string.stop_the_mic)
            startSpeechToText()
            binding.ivMic.tag = "stop"
        } else {
            binding.ivMic.tag = "start"
            Toast.makeText(requireContext(), "Stopped", Toast.LENGTH_SHORT).show()
            binding.tvPressMic.text = requireActivity().getString(R.string.press_the_mic)
            stopSpeechToText()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_mic -> {
                if (Permissions.checkVoiceRecordingPermission(requireContext())) {
                    playPauseSpeechToText()
                } else {
                    recordAndStoreAudioPerm.launch(
                        arrayOf(
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    )
                }
            }

            R.id.tv_save_text -> {
                if(binding.etText.text.isNotEmpty()) {
                    try {
                        GlobalScope.launch {
                            SpeechToTextDatabase.getInstance(requireContext()).dao.saveSpeechToText(
                                SpeechToTextModel(
                                    null,
                                    binding.etText.text.toString(),
                                    Utils.getDateAndTime()
                                )
                            )
                            withContext(Dispatchers.Main) {
                                savedSpeeches = null
                                adapter = null
                                retrieveAllSpeechTexts()
                                Toast.makeText(requireContext(),"Saved", Toast.LENGTH_SHORT).show()
                                binding.etText.setText("")
                            }
                        }

                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            }

            R.id.tv_copy_text -> {
                if(binding.etText.text.isNotEmpty())
                    onCopyClipBoard(binding.etText.text.toString())
            }

            R.id.tv_share_text -> {
                if(binding.etText.text.isNotEmpty())
                    Launcher.shareText(binding.etText.text.toString(), requireActivity())
            }
        }
    }

    private fun onCopyClipBoard(text: String) {
        val clipboard = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText(text, text)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "Text Copied", Toast.LENGTH_SHORT).show()
    }

    override fun onReadyForSpeech(p0: Bundle?) {
    }

    override fun onBeginningOfSpeech() {
    }

    override fun onRmsChanged(p0: Float) {
    }

    override fun onBufferReceived(p0: ByteArray?) {
    }

    override fun onEndOfSpeech() {
    }

    override fun onError(error: Int) {
        speech.startListening(Launcher.startSpeechRecognizer())
        if (Utils.generateErrorText(error) != "No Match") {
            Toast.makeText(requireContext(), Utils.generateErrorText(error), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onResults(results: Bundle?) {
        speech.startListening(Launcher.startSpeechRecognizer())

        var text = ""
        speechResult?.addAll(results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!!)
        for (result in speechResult!!)
            text += """ $result""".trimIndent()
        binding.etText.setText(text)
    }

    override fun onPartialResults(p0: Bundle?) {
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
    }
}
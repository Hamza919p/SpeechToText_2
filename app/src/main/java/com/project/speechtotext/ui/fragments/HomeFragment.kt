package com.project.speechtotext.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.project.speechtotext.OnClickListener
import com.project.speechtotext.R
import com.project.speechtotext.adapters.HomeTabsAdapter
import com.project.speechtotext.databinding.FragmentHomeBinding
import com.project.speechtotext.ui.fragments.homeChildFragments.DocumentScanFragment
import com.project.speechtotext.ui.fragments.homeChildFragments.SpeechToTextFragment
import com.project.speechtotext.ui.fragments.homeChildFragments.TextToSpeechFragment
import com.project.speechtotext.ui.fragments.homeChildFragments.TranslatorFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    companion object {
        private var instance: HomeFragment? = null

        @Synchronized
        fun getInstance(): HomeFragment? {
            if (instance == null) instance = HomeFragment()
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        init()
        return binding.root
    }


    private fun init() {
        initializeTabsAdapter()
        replaceFragment(SpeechToTextFragment.getInstance()!!)
    }

    private fun replaceFragment(fragment: Fragment) {
        val ft = childFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, fragment)
        ft.commit()
    }

    private fun initializeTabsAdapter() {
        val tabTitles = listOf("Speech To Text", "Text To Speech", "Document Scan")
        val icons = listOf(
            AppCompatResources.getDrawable(requireContext(), R.drawable.speech_to_text_selector)!!,
            AppCompatResources.getDrawable(requireContext(), R.drawable.text_to_speech_selector)!!,
            AppCompatResources.getDrawable(requireContext(), R.drawable.document_scan_selector)!!,
//            AppCompatResources.getDrawable(requireContext(), R.drawable.translator_selector)!!
        )
        val adapter = HomeTabsAdapter(requireActivity(), tabTitles, icons)
        binding.rvTabs.adapter = adapter
        adapter.observeClickListener(object : OnClickListener {
            override fun onClick(position: Int) {
                changeScreen(position)
            }
        })
    }

    private fun changeScreen(position: Int) {
        when (position) {
            0 -> {
                replaceFragment(SpeechToTextFragment.getInstance()!!)
            }
            1 -> {
                replaceFragment(TextToSpeechFragment.getInstance()!!)
            }
            2 -> {
                replaceFragment(DocumentScanFragment.getInstance()!!)
            }
            3 -> {
                replaceFragment(TranslatorFragment.getInstance()!!)
            }
        }
    }

}
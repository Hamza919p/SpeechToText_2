package com.project.speechtotext.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.project.speechtotext.R
import com.project.speechtotext.databinding.FragmentLanguageBinding
import com.project.speechtotext.adapters.LanguagesAdapter

class LanguageFragment : Fragment() {
    private lateinit var binding: FragmentLanguageBinding

    companion object {
        private var instance: LanguageFragment? = null

        @Synchronized
        fun getInstance(): LanguageFragment? {
            if (instance == null) instance = LanguageFragment()
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_language, container, false)
        initializeAdapter()
        return binding.root
    }

    private fun initializeAdapter() {
        val languages = listOf(
            "English",
            "Russia",
            "German",
            "Arabic",
            "Chinese",
            "Spanish",
            "Turkish",
            "Bangladesh",
            "Irani",
            "Urud"
        )
        val adapter = LanguagesAdapter(languages)
        binding.rvLanguages.adapter = adapter
    }

}
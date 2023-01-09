package com.project.speechtotext.ui.fragments.homeChildFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.speechtotext.R

class TranslatorFragment private constructor(): Fragment() {

    companion object {
        private var instance: TranslatorFragment?= null
        @Synchronized
        fun getInstance() : TranslatorFragment? {
            if(instance == null) instance = TranslatorFragment()
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translator, container, false)
    }

}
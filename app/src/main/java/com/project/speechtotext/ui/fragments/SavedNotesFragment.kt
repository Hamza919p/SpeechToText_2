package com.project.speechtotext.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.project.speechtotext.*
import com.project.speechtotext.adapters.SavedNotesAdapter
import com.project.speechtotext.databinding.FragmentSavedNotesBinding
import com.project.speechtotext.adapters.SavedNotesTabsAdapter
import com.project.speechtotext.data.NotesModel
import com.project.speechtotext.data.SpeechToTextDatabase
import com.project.speechtotext.data.SpeechToTextModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedNotesFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentSavedNotesBinding
    private var savedNotesAdapter: SavedNotesAdapter ?= null

    companion object {
        private var instance: SavedNotesFragment?= null
        @Synchronized
        fun getInstance() : SavedNotesFragment? {
            if(instance == null) instance = SavedNotesFragment()
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_notes, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.floatingButton.setOnClickListener(this)
//        initializeTabAdapter()
        retrieveAllSavedNotes()
    }

    private fun initializeTabAdapter() {
        val tabs = listOf("Notes", "Speech", "Documents")
        val adapter = SavedNotesTabsAdapter(requireActivity(), tabs)
        binding.rvTabs.adapter = adapter
        adapter.observeOnClickListener(object : OnClickListener{
            override fun onClick(position: Int) {

            }
        })
    }

    private fun initializeNotesAdapter(notes: List<NotesModel>) {
        savedNotesAdapter = SavedNotesAdapter(notes)
        binding.rvNotes.adapter = savedNotesAdapter
        savedNotesAdapter?.observerClickListener(object : OnClickListener{
            override fun onClick(position: Int) {
                Launcher.startEditNoteActivity(requireActivity(),notes[position].text)
            }
        })
    }

    private fun openAddNoteDialog() {
        val addNotDialog = AddNoteDialog(requireActivity())
        addNotDialog.show()
        addNotDialog.setOnDismissListener(object : AddNoteDialog.OnDismissListener{
            override fun onDismiss(value: String) {
                if(value != "") {
                    saveToRoom(value)
                }
            }
        })
    }

    private fun saveToRoom(note: String) {
        try {
            GlobalScope.launch {
                SpeechToTextDatabase.getInstance(requireContext()).dao.saveNotes(
                    NotesModel(
                        null,
                        note
                    )
                )
                withContext(Dispatchers.Main) {
                    savedNotesAdapter = null
                    retrieveAllSavedNotes()
                    Toast.makeText(requireContext(),"Saved", Toast.LENGTH_SHORT).show()
                }
            }

        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun retrieveAllSavedNotes() {
        GlobalScope.launch {
            val savedSpeeches = SpeechToTextDatabase.getInstance(requireContext()).dao.getAllNotes()
            withContext(Dispatchers.Main) {
                initializeNotesAdapter(savedSpeeches)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.floating_button -> {
                openAddNoteDialog()
            }
        }
    }

}
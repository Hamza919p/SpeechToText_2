package com.project.speechtotext.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.speechtotext.OnClickListener
import com.project.speechtotext.R
import com.project.speechtotext.data.NotesModel


class SavedNotesAdapter(var notes: List<NotesModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onClickListener: OnClickListener
    fun observerClickListener(mCallback: OnClickListener) {
        this.onClickListener = mCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_view, parent, false)
        return SavedNotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SavedNotesViewHolder
        holder.note.text = notes[position].text
        holder.note.setOnClickListener {
            onClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
       return notes.size
    }


    private inner class SavedNotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val note: TextView = itemView.findViewById(R.id.tv_note)
    }
}
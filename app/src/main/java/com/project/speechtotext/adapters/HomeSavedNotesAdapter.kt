package com.project.speechtotext.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.speechtotext.OnClickListener
import com.project.speechtotext.R
import com.project.speechtotext.data.SpeechToTextModel

class HomeSavedNotesAdapter(var isSpeechToText: Boolean, var speeches: List<SpeechToTextModel>?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private lateinit var onClickListener: OnClickListener
    fun observerClickListener(mCallback: OnClickListener) {
        onClickListener = mCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_notes_view, parent, false)
        return HomeSavedNotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as HomeSavedNotesViewHolder
        if (!isSpeechToText) {
            holder.title.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_volume_2,0)
        }
        holder.title.text = speeches!![position].text
        holder.title.setOnClickListener {
            onClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return speeches?.size!!
    }

    private inner class HomeSavedNotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_text)
    }
}
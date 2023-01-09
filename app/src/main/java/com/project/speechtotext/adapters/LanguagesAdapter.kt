package com.project.speechtotext.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.speechtotext.R

class LanguagesAdapter(val languages: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.languages_view, parent, false)
        return LanguagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as LanguagesViewHolder
        holder.language.text = languages[position]
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    private inner class LanguagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val language: TextView = itemView.findViewById(R.id.tv_language_or_notes_tab)
    }
}
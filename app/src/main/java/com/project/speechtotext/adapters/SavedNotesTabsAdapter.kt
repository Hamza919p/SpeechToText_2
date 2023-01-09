package com.project.speechtotext.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.speechtotext.OnClickListener
import com.project.speechtotext.R


class SavedNotesTabsAdapter(private val activity: Activity, private val tabs: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var selectedTabPos = 0

    private lateinit var onClickListener: OnClickListener
    fun observeOnClickListener(mCallback: OnClickListener) {
        onClickListener = mCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_notes_tab_view, parent, false)
        return SavedNotesTabsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SavedNotesTabsViewHolder
        holder.tabText.text = tabs[position]
        holder.tabText.setOnClickListener{
            onClickListener.onClick(position)
            selectedTabPos = position
            notifyDataSetChanged()
        }

        if(selectedTabPos == position) {
            holder.tabText.setBackgroundResource(R.drawable.bg_red_round_corners_8_filled)
            holder.tabText.setTextColor(activity.getColor(R.color.white))
        } else {
            holder.tabText.setBackgroundResource(R.drawable.bg_red_round_corners_8)
            holder.tabText.setTextColor(activity.getColor(R.color.color_primary))
        }
    }

    override fun getItemCount(): Int {
        return tabs.size
    }

    private inner class SavedNotesTabsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tabText: TextView = itemView.findViewById(R.id.tv_tab)
    }
}
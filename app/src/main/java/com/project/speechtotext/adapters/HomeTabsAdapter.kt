package com.project.speechtotext.adapters

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.speechtotext.OnClickListener
import com.project.speechtotext.R


class HomeTabsAdapter(var activity: Activity, var titles: List<String>, var icons: List<Drawable>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var selectedTabPos = 0

    private lateinit var onItemClickListener: OnClickListener
    fun observeClickListener(mCallback: OnClickListener) {
        this.onItemClickListener = mCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_tab_view, parent, false)
        return HomeTabsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as HomeTabsViewHolder
        holder.title.text = titles[position]
        holder.icon.setImageDrawable(icons[position])

        holder.layout.setOnClickListener {
            selectedTabPos = position
            notifyDataSetChanged()
            onItemClickListener.onClick(position)
        }

        if(selectedTabPos == position) {
            holder.icon.isSelected = true
            holder.title.setTextColor(activity.getColor(R.color.white))
            holder.layout.setBackgroundResource(R.drawable.bg_red_round_corners_8_filled)
        } else {
            holder.icon.isSelected = false
            holder.title.setTextColor(activity.getColor(R.color.color_primary))
            holder.layout.setBackgroundResource(R.drawable.bg_red_round_corners_8)
        }

    }

    override fun getItemCount(): Int {
        return titles.size
    }

    private inner class HomeTabsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView.findViewById(R.id.linear_layout)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val icon: ImageView = itemView.findViewById(R.id.iv_icon)
    }
}
package com.example.myapplication.Diary

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.GridBookmarkItemBinding

class DiaryMainBookmarkAdapter(
    val context: Context,
    val items: ArrayList<DiaryMainBookmarkData>
) : RecyclerView.Adapter<DiaryMainBookmarkAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: GridBookmarkItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DiaryMainBookmarkData) {
            Glide.with(context)
                .load(item.imageUrl)
                .centerCrop()
                .into(binding.ivDay)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GridBookmarkItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

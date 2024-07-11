package com.example.myapplication.Diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.CardviewItemBinding

class CardviewAdapter(private var items: List<DiaryMainDayData>) : RecyclerView.Adapter<CardviewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardviewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            tvDate.text = "${item.year}.${item.month}.${item.day}"
            Glide.with(holder.itemView.context)
                .load(item.imageResId)
                .into(ivDiaryImage)
            tvContent.text = item.content
        }
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<DiaryMainDayData>) {
        items = newItems
        notifyDataSetChanged()
    }
}
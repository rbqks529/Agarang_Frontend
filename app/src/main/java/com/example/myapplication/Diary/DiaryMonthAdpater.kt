package com.example.myapplication.Diary

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.DiaryMonthItemBinding

class DiaryMonthAdapter(val context: Context, val items: List<DiaryMainDayData>) : RecyclerView.Adapter<DiaryMonthAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: DiaryMonthItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DiaryMainDayData) {
            binding.tvMonth.text = "${item.year} / ${item.month.toString().padStart(2, '0')}"

            Glide.with(context)
                .load(item.imageUrl)
                .centerCrop()
                .into(binding.ivMonth)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DiaryMonthItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("DiaryMonthAdapter", "Binding item at position $position")
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
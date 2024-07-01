package com.example.myapplication.Diary

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.GridItemBinding

class DiaryMonthAdapter (val context: Context, val items: ArrayList<DiaryMonthData>) : RecyclerView.Adapter<DiaryMonthAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: GridItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : DiaryMonthData){
            binding.tvDay.text = item.date.toString()

            Glide.with(context)
                .load(item.imageResId)
                .centerCrop()
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("DiaryMonthAdapter", "Binding item at position $position")
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
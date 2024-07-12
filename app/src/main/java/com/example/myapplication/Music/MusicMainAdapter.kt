package com.example.myapplication.Music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Diary.DiaryDayAdapter
import com.example.myapplication.databinding.MusicItemBinding

class MusicMainAdapter(val items:ArrayList<MusicMainData>):RecyclerView.Adapter<MusicMainAdapter.ViewHolder>() {
    private lateinit var itemClickListener:DiaryDayAdapter.OnItemClickListener
    inner class ViewHolder (val binding:MusicItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: MusicMainData){
            binding.ivRvCover1.setImageResource(item.musicImgId)
            binding.tvCover1.text=item.musicContent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicMainAdapter.ViewHolder {
        val binding=MusicItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
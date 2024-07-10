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
            tvDate.text = "${item.year}년 ${item.month}월 ${item.day}일"
            Glide.with(holder.itemView.context)
                .load(item.imageResId)
                .into(ivDiaryImage)
            tvContent.text = "오늘 엄마랑 대관령에 다녀왔단다. \n" +
                    "오똑숲에서 많은 나무와 아름다운 할미 꽃을 보며 정말 행복했어. 자연 속에서 느낀 피톤치드 덕분에 마음이 \n" +
                    "한결 편안해졌단다. 우리 아가도 나중에 꼭 같이 가보자. 사랑해. 우리 아가!" // 실제 데이터로 대체해야 합니다
        }
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<DiaryMainDayData>) {
        items = newItems
        notifyDataSetChanged()
    }
}
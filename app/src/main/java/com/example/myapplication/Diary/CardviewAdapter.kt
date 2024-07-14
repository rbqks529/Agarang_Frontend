package com.example.myapplication.Diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.CardviewItemBinding

class CardviewAdapter(private var items: MutableList<DiaryMainDayData>) : RecyclerView.Adapter<CardviewAdapter.ViewHolder>() {

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

            // 북마크 상태에 따라 이미지 설정
            ivBookmark.setImageResource(
                if (item.bookmark == 1) R.drawable.ic_heart_red
                else R.drawable.ic_heart_gray
            )

            ivBookmark.setOnClickListener {
                // 북마크 상태 토글
                item.bookmark = if (item.bookmark == 0) 1 else 0

                // 이미지 업데이트
                ivBookmark.setImageResource(
                    if (item.bookmark == 1) R.drawable.ic_heart_red
                    else R.drawable.ic_heart_gray
                )

                // 데이터 변경 알림
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount() = items.size

}
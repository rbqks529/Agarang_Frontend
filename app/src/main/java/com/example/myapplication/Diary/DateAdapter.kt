package com.example.myapplication.Diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.DateItemBinding
import java.util.Calendar

class DateAdapter(private var items: List<DiaryMainDayData>, private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<DateAdapter.ViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION

    inner class ViewHolder(val binding: DateItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            tvWeekday.text = getWeekday(item.year, item.month, item.day)

            if (position == selectedPosition) {
                ivDiaryThumbnail.visibility = View.GONE
                tvDay.visibility = View.VISIBLE
                tvDayBelow.visibility = View.GONE
                tvDay.text = item.day.toString()
                tvDay.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.selected_date_color))
                tvDay.setBackgroundResource(R.drawable.selected_date_background)
            } else {
                ivDiaryThumbnail.visibility = View.VISIBLE
                tvDay.visibility = View.GONE
                tvDayBelow.visibility = View.VISIBLE
                tvDayBelow.text = item.day.toString()
                Glide.with(holder.itemView.context)
                    .load(item.imageUrl)
                    .into(ivDiaryThumbnail)
            }

            root.setOnClickListener {
                onItemClick(position)
                updateSelectedPosition(position)
            }
        }
    }

    fun updateSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }

    override fun getItemCount() = items.size

    private fun getWeekday(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "일"
            Calendar.MONDAY -> "월"
            Calendar.TUESDAY -> "화"
            Calendar.WEDNESDAY -> "수"
            Calendar.THURSDAY -> "목"
            Calendar.FRIDAY -> "금"
            Calendar.SATURDAY -> "토"
            else -> ""
        }
    }
}
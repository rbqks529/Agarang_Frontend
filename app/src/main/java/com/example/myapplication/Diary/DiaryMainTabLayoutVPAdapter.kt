package com.example.myapplication.Diary

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DiaryMainTabLayoutVPAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DiaryMainBookmarkFragment()
            1 -> DiaryMainMonthFragment()
            else -> DiaryMainDayFragment()
        }
    }
}
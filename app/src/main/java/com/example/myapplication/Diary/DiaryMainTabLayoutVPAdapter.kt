package com.example.myapplication.Diary

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DiaryMainTabLayoutVPAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private var dayFragment: DiaryMainDayFragment? = null

    private val fragments = arrayOf(
        { DiaryMainBookmarkFragment() },
        { DiaryMainMonthFragment() },
        { DiaryMainDayFragment() } // 이 부분에서 반환된 프래그먼트를 dayFragment에 할당
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        val fragment = fragments[position].invoke()
        if (position == 2 && fragment is DiaryMainDayFragment) {
            dayFragment = fragment
        }
        return fragment
    }

    fun getDayFragment(): DiaryMainDayFragment? {
        return dayFragment
    }

}

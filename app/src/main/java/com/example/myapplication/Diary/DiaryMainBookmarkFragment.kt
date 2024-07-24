package com.example.myapplication.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDiaryMainBookmarkBinding


class DiaryMainBookmarkFragment : Fragment() {

    lateinit var binding: FragmentDiaryMainBookmarkBinding
    private var DiaryBookmarkAdapter : DiaryMainBookmarkAdapter?= null
    private var DiaryBookmarkitemList : ArrayList<DiaryMainDayData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryMainBookmarkBinding.inflate(inflater, container, false)

        //데이터 생성
        initData()
        //RecyclerView 생성
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val spanCount = 3 // 열의 수
        DiaryBookmarkAdapter = DiaryMainBookmarkAdapter(requireContext(), DiaryBookmarkitemList)
        binding.rvDiaryDay.adapter = DiaryBookmarkAdapter
        binding.rvDiaryDay.layoutManager = GridLayoutManager(context, spanCount)

        // 아이템 크기 고정
        binding.rvDiaryDay.addItemDecoration(SquareItemDecoration(spanCount))
    }

    private fun initData() {
    }


}
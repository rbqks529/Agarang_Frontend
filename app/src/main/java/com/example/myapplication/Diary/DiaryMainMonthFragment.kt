package com.example.myapplication.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDiaryMainMonthBinding


class DiaryMainMonthFragment : Fragment() {

    lateinit var binding: FragmentDiaryMainMonthBinding
    private var DiaryMonthAdapter : DiaryMonthAdapter?= null
    private var DiaryMonthitemList : ArrayList<DiaryMonthData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDiaryMainMonthBinding.inflate(inflater, container, false)

        //데이터 생성
        initData()
        //RecyclerView 생성
        initRecyclerView()

        binding.btnFavorite.setOnClickListener { /* 즐겨찾기 처리 */ }
        binding.btnMonth.setOnClickListener { /* 월 보기 처리 */ }
        binding.btnDay.setOnClickListener { /* 일 보기 처리 */ }

        return binding.root
    }

    private fun initRecyclerView() {
        val spanCount = 3 // 열의 수
        DiaryMonthAdapter = DiaryMonthAdapter(requireContext(), DiaryMonthitemList)
        binding.rvDiary.adapter = DiaryMonthAdapter
        binding.rvDiary.layoutManager = GridLayoutManager(context, spanCount)

        // 아이템 크기 고정
        binding.rvDiary.addItemDecoration(SquareItemDecoration(spanCount))

        // 아이템 간 간격 설정
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        binding.rvDiary.addItemDecoration(GridSpacingItemDecoration(spanCount, spacingInPixels, false))

    }

    private fun initData() {
        DiaryMonthitemList.addAll(
            arrayListOf(
                DiaryMonthData(R.drawable.post_sample, 1),
                DiaryMonthData(R.drawable.post_sample, 2),
                DiaryMonthData(R.drawable.post_sample, 3),
                DiaryMonthData(R.drawable.post_sample, 4),
                DiaryMonthData(R.drawable.post_sample, 5),
                DiaryMonthData(R.drawable.post_sample, 6),
                DiaryMonthData(R.drawable.post_sample, 7),
                DiaryMonthData(R.drawable.post_sample, 8),
                DiaryMonthData(R.drawable.post_sample, 9),
                DiaryMonthData(R.drawable.post_sample, 10),
                DiaryMonthData(R.drawable.post_sample, 11),
                DiaryMonthData(R.drawable.post_sample, 12),
                DiaryMonthData(R.drawable.post_sample, 13),
                DiaryMonthData(R.drawable.post_sample, 14),
                DiaryMonthData(R.drawable.post_sample, 15),
                DiaryMonthData(R.drawable.post_sample, 16),
                DiaryMonthData(R.drawable.post_sample, 17),
                DiaryMonthData(R.drawable.post_sample, 18),
                DiaryMonthData(R.drawable.post_sample, 19),
                DiaryMonthData(R.drawable.post_sample, 20),
                DiaryMonthData(R.drawable.post_sample, 21)
            )
        )

    }

}

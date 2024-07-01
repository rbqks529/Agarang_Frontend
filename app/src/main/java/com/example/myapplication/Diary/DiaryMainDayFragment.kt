package com.example.myapplication.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDiaryMainDayBinding


class DiaryMainDayFragment : Fragment() {

    lateinit var binding: FragmentDiaryMainDayBinding
    private var DiaryMonthAdapter : DiaryDayAdapter?= null
    private var DiaryMonthitemList : ArrayList<DiaryMainDayData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDiaryMainDayBinding.inflate(inflater, container, false)

        //데이터 생성
        initData()
        //RecyclerView 생성
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val spanCount = 3 // 열의 수
        DiaryMonthAdapter = DiaryDayAdapter(requireContext(), DiaryMonthitemList)
        binding.rvDiary.adapter = DiaryMonthAdapter
        binding.rvDiary.layoutManager = GridLayoutManager(context, spanCount)

        // 아이템 크기 고정
        binding.rvDiary.addItemDecoration(SquareItemDecoration(spanCount))

        /*// 아이템 간 간격 설정
        val spacingHorizontal = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        val spacingVertical = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        binding.rvDiary.addItemDecoration(GridSpacingItemDecoration(spanCount, spacingHorizontal, spacingVertical))*/
    }

    private fun initData() {
        DiaryMonthitemList.addAll(
            arrayListOf(
                DiaryMainDayData(R.drawable.post_sample, 1),
                DiaryMainDayData(R.drawable.post_sample, 2),
                DiaryMainDayData(R.drawable.post_sample, 3),
                DiaryMainDayData(R.drawable.post_sample, 4),
                DiaryMainDayData(R.drawable.post_sample, 5),
                DiaryMainDayData(R.drawable.post_sample, 6),
                DiaryMainDayData(R.drawable.post_sample, 7),
                DiaryMainDayData(R.drawable.post_sample, 8),
                DiaryMainDayData(R.drawable.post_sample, 9),
                DiaryMainDayData(R.drawable.post_sample, 10),
                DiaryMainDayData(R.drawable.post_sample, 11),
                DiaryMainDayData(R.drawable.post_sample, 12),
                DiaryMainDayData(R.drawable.post_sample, 13),
                DiaryMainDayData(R.drawable.post_sample, 14),
                DiaryMainDayData(R.drawable.post_sample, 15),
                DiaryMainDayData(R.drawable.post_sample, 16),
                DiaryMainDayData(R.drawable.post_sample, 17),
                DiaryMainDayData(R.drawable.post_sample, 18),
                DiaryMainDayData(R.drawable.post_sample, 19),
                DiaryMainDayData(R.drawable.post_sample, 20),
                DiaryMainDayData(R.drawable.post_sample, 21)
            )
        )

    }

}

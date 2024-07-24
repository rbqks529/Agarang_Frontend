package com.example.myapplication.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.Retrofit.DiaryIF
import com.example.myapplication.Retrofit.RetrofitService
import com.example.myapplication.databinding.FragmentDiaryMainMonthBinding

class DiaryMainMonthFragment : Fragment() {

    lateinit var binding: FragmentDiaryMainMonthBinding
    private var DiaryMonthAdapter : DiaryMonthAdapter?= null
    private var DiaryMonthitemList : ArrayList<DiaryMainDayData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryMainMonthBinding.inflate(inflater, container, false)


        val retrofit = RetrofitService.retrofit
        val service = retrofit.create(DiaryIF::class.java)

        //데이터 생성
        initData()
        //RecyclerView 생성
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val spanCount = 3 // 열의 수
        DiaryMonthAdapter = DiaryMonthAdapter(requireContext(), DiaryMonthitemList)
        binding.rvDiaryMonth.adapter = DiaryMonthAdapter
        binding.rvDiaryMonth.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

    }

    private fun initData() {
        DiaryMonthitemList.addAll(
            arrayListOf(
                DiaryMainDayData(R.drawable.post_sample, "2024 / 3 / 1"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 2"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 4"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 4 / 5"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 6"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 7"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 8"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 9"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 10"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 11"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 12"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 13"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 14"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 15"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 16"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 17"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 18"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 6 / 19"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 20"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 21"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 7 / 22")
            )
        )

    }

}
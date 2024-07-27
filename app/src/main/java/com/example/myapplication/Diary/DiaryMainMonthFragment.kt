package com.example.myapplication.Diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Retrofit.DiaryIF
import com.example.myapplication.Retrofit.RetrofitService
import com.example.myapplication.databinding.FragmentDiaryMainMonthBinding
import com.example.myapplication.Data.Response.DiaryMonthResponse
import com.example.myapplication.Data.Response.MonthlyMemory
import com.example.myapplication.Retrofit.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryMainMonthFragment : Fragment() {

    lateinit var binding: FragmentDiaryMainMonthBinding
    private var DiaryMonthAdapter: DiaryMonthAdapter? = null
    private var DiaryMonthitemList: ArrayList<DiaryMainDayData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryMainMonthBinding.inflate(inflater, container, false)

        fetchMonthlyMemories()
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        DiaryMonthAdapter = DiaryMonthAdapter(requireContext(), DiaryMonthitemList)
        binding.rvDiaryMonth.adapter = DiaryMonthAdapter
        binding.rvDiaryMonth.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    private fun fetchMonthlyMemories() {
        val service = RetrofitService.retrofit.create(DiaryIF::class.java)

        service.getMonthlyMemories("monthly").enqueue(object : Callback<DiaryMonthResponse> {
            override fun onResponse(call: Call<DiaryMonthResponse>, response: Response<DiaryMonthResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    if (apiResponse != null && apiResponse.isSuccess) {
                        updateRecyclerView(apiResponse.result.monthlyMemories)
                    } else {
                        // 에러 처리
                    }
                } else {
                    // 오류 응답 처리
                    val errorBody = response.errorBody()?.string()
                    Log.e("오류", "Response error: $errorBody")
                }
            }
            override fun onFailure(call: Call<DiaryMonthResponse>, t: Throwable) {
                Log.d("실패", t.message.toString())
            }
        })
    }

    private fun updateRecyclerView(memories: List<MonthlyMemory>) {
        DiaryMonthitemList.clear()
        DiaryMonthitemList.addAll(memories.map { memory ->
            DiaryMainDayData(
                imageUrl = memory.imageUrl,
                date = memory.date,
                content = "" // API에서 제공하지 않는 정보라면 빈 문자열로 설정
            )
        })
        DiaryMonthAdapter?.notifyDataSetChanged()
    }
}
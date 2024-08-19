package com.example.myapplication.Diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.Data.Response.DailyMemory
import com.example.myapplication.Data.Response.DiaryDayResponse
import com.example.myapplication.R
import com.example.myapplication.Retrofit.DiaryIF
import com.example.myapplication.Retrofit.RetrofitService
import com.example.myapplication.databinding.FragmentDiaryMainDayBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DiaryMainDayFragment : Fragment() {

    private lateinit var binding: FragmentDiaryMainDayBinding
    private var diaryDayAdapter: DiaryDayAdapter? = null
    private var diaryDayItemList: ArrayList<DiaryMainDayData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiaryMainDayBinding.inflate(inflater, container, false)

        fetchDailyMemories()
        initRecyclerView()

        return binding.root
    }

    private fun fetchDailyMemories() {
        val service = RetrofitService.createRetrofit(requireContext()).create(DiaryIF::class.java)

        service.getDailyMemories("daily").enqueue(object : Callback<DiaryDayResponse> {
            override fun onResponse(call: Call<DiaryDayResponse>, response: Response<DiaryDayResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    if (apiResponse != null && apiResponse.isSuccess) {
                        updateRecyclerView(apiResponse.result.dailyMemories)
                    } else {
                        // 에러 처리
                        Log.e("오류", "API 요청이 성공하지 못했습니다: ${apiResponse?.message}")
                    }
                } else {
                    // 오류 응답 처리
                    val errorBody = response.errorBody()?.string()
                    Log.e("오류", "Response error: $errorBody")
                }
            }

            override fun onFailure(call: Call<DiaryDayResponse>, t: Throwable) {
                Log.e("실패", "API 요청 실패: ${t.message}")
            }
        })

    }

    private fun updateRecyclerView(memories: List<DailyMemory>) {
        diaryDayItemList.addAll(memories.map { memory ->
            DiaryMainDayData(
                id = memory.id,
                date = memory.date,
                imageResId = memory.imageUrl ?: ""
            )
        })
        diaryDayAdapter?.notifyDataSetChanged()

        // 아이템이 로드된 후 마지막 아이템으로 스크롤
        scrollToLastItem()
    }

    private fun initRecyclerView() {
        val spanCount = 3
        diaryDayAdapter = DiaryDayAdapter(requireContext(), diaryDayItemList)
        binding.rvDiaryDay.apply {
            adapter = diaryDayAdapter
            layoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(SquareItemDecoration(spanCount))
        }

        diaryDayAdapter?.setOnItemClickListener(object : DiaryDayAdapter.OnItemClickListener {
            override fun onItemClick(item: DiaryMainDayData) {
                navigateToDiaryMainCard(item)
            }
        })


    }
    private fun scrollToLastItem() {
        // 아이템이 있는 경우에만 스크롤
        if (diaryDayItemList.isNotEmpty()) {
            binding.rvDiaryDay.scrollToPosition(diaryDayItemList.size - 1)
        }
    }

    private fun navigateToDiaryMainCard(item: DiaryMainDayData) {
        val fragment = DiaryMainCardFragment()

        // 날짜에서 월 추출 (예: "2023-08-19"에서 8월 추출)
        val selectedMonth = extractMonth(item.date)

        // 선택한 월에 속하는 아이템들만 필터링
        val filteredList = diaryDayItemList.filter { extractMonth(it.date) == selectedMonth }

        // 선택한 아이템의 월 내 인덱스 구하기
        val positionInMonth = filteredList.indexOf(item)

        val bundle = Bundle().apply {
            putSerializable("data", ArrayList(diaryDayItemList))
            Log.d("position", "$positionInMonth")
            putInt("position", positionInMonth)
            putString("date", item.date) // 선택한 아이템의 날짜 전달
        }
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction().replace(R.id.main_frm, fragment)
            .commit()
    }


    private fun extractMonth(date: String): Int {
        // 날짜 형식에 맞게 SimpleDateFormat 정의 (예: "yyyy-MM-dd")
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val parsedDate = dateFormat.parse(date)
        val calendar = Calendar.getInstance().apply { time = parsedDate }
        return calendar.get(Calendar.MONTH) + 1 // 월은 0부터 시작하므로 +1 해줌
    }


}
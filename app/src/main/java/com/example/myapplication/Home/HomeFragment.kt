package com.example.myapplication.Home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.Data.Response.HomeResponse
import com.example.myapplication.R


import com.example.myapplication.Retrofit.DiaryIF
import com.example.myapplication.Retrofit.HomeIF
import com.example.myapplication.Retrofit.RetrofitService
import com.example.myapplication.Setting.ChildInfoChangeFragment

import com.example.myapplication.Setting.HomeSettingFragment
import com.example.myapplication.SharedViewModel
import com.example.myapplication.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment: Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var RecentDiaryAdapter: RecentDiaryAdapter?= null
    private var RecentDiaryDataList : ArrayList<RecentDiaryData> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        //설정화면으로 전환
        binding.ivSetting.setOnClickListener {
            val fragmentSetting=HomeSettingFragment()
            val transaction=parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frm,fragmentSetting)
                .commit()
        }
//shared preference, 현재 날짜 가져오는 코드가 필요 없었던 것
//전부 서버에서 가져오는 걸로 수정

        /*initData()
        initRecyclerView()

        //설정화면으로 전환
        binding.ivSetting.setOnClickListener {
            val fragmentSetting=HomeSettingFragment()
            val transaction=parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frm,fragmentSetting)
                .commit()
        }

        // 현재 날짜 가져오기
        val calendar = Calendar.getInstance()
        val currentDate = calendar.time
        // 날짜를 특정 형식의 문자열로 변환
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)
        binding.tvToday.text = formattedDate

        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val selectedChar = sharedPreferences.getInt("selected_char", -1)

        if (selectedChar != -1) {
            // selectedChar 값을 사용하여 작업 수행
            binding.ivBabyTiger.setImageResource(selectedChar)
        }*/

        // 모든 관련 뷰를 invisible로 설정
        setViewsVisibility(View.INVISIBLE)

        binding.customCircleBarView.setProgress(0f)  // 50% 진행 (예시)
        fetchRecentDiary()
        initRecyclerView()


        return binding.root
    }

    private fun setViewsVisibility(visibility: Int) {
        binding.tvToday.visibility = visibility
        binding.tvDDayText.visibility = visibility
        binding.tvDDay.visibility = visibility
        binding.tvSpeechBubble.visibility = visibility
        binding.customCircleBarView.visibility = visibility
        binding.ivBabyTiger.visibility = visibility
        binding.rvRecentCard.visibility = visibility
        binding.ivRectangle1.visibility = visibility
        binding.ivRectangle2.visibility = visibility
    }

    private fun fetchRecentDiary(){
        val service = RetrofitService.createRetrofit(requireContext()).create(HomeIF::class.java)

        service.getHomeData().enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    if (apiResponse != null && apiResponse.isSuccess) {
                        updateUIWithServerData(apiResponse.result)
                        updateRecyclerView(apiResponse.result.memoryIds)
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

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                Log.e("실패", "API 요청 실패: ${t.message}")
            }
        })
    }

    private fun updateUIWithServerData(result: HomeResponse.Result) {
        // 날짜 형식 변환
        val originalDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val targetDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val formattedDate = targetDateFormat.format(originalDateFormat.parse(result.today))

        // UI 업데이트
        binding.tvToday.text = formattedDate
        binding.tvDDayText.text = "${result.babyName}가 태어나기까지"
        binding.tvDDay.text = "D-${result.dday}"
        binding.tvSpeechBubble.text = result.speechBubble

        // D-day를 정수로 변환
        val dday = result.dday

        // 280일 기준으로 progress 계산
        val progress = ((280 - dday) / 280.0f) * 360f
        binding.customCircleBarView.setProgress(progress)

        // 이미지 URL을 이미지뷰에 로드 (Glide, Picasso 등을 사용할 수 있습니다)

        activity?.let {
            Glide.with(this).load(result.characterUrl).into(binding.ivBabyTiger)
        }

            setViewsVisibility(View.VISIBLE)
//
//        Glide.with(this)
//            .load(result.characterUrl)
//            .into(binding.ivBabyTiger)
//        if (isAdded) {
//            Glide.with(requireContext())
//                .load(result.characterUrl)
//                .into(binding.ivBabyTiger)
//       }


    }


    private fun updateRecyclerView(memoryIds: List<String>) {
        // 서버에서 받아온 memoryUrls을 리사이클러뷰에 반영
        RecentDiaryDataList.clear()  // 기존 데이터 초기화
        RecentDiaryDataList.addAll(memoryIds.map { url ->
            RecentDiaryData("내용", url) // 내용은 임의로 설정
        })
        RecentDiaryAdapter?.notifyDataSetChanged() // 데이터 변경을 어댑터에 알림
    }

    private fun initRecyclerView(){
        val spanCount = 3 // 열의 수
        RecentDiaryAdapter = RecentDiaryAdapter(RecentDiaryDataList)
        binding.rvRecentCard.adapter = RecentDiaryAdapter
        binding.rvRecentCard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }



}
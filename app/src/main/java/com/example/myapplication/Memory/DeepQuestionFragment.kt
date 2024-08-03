package com.example.myapplication.Memory

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsAnimation
import com.example.myapplication.Data.Request.Memory2Request
import com.example.myapplication.Data.Response.Memory2Response
import com.example.myapplication.R
import com.example.myapplication.Retrofit.DiaryIF
import com.example.myapplication.Retrofit.RetrofitService
import com.example.myapplication.databinding.FragmentDeepQuestionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeepQuestionFragment : Fragment() {
    private lateinit var binding:FragmentDeepQuestionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDeepQuestionBinding.inflate(inflater,container,false)
        binding.ivRecordBtn.setOnClickListener {
            binding.ivRecordBtn.visibility=View.GONE
            binding.tvRecordNotice.visibility=View.VISIBLE
            binding.ivRecordCancleBtn.visibility=View.VISIBLE
            binding.ivRecordArrowBtn.visibility=View.VISIBLE
            binding.ivRecordIng.visibility=View.VISIBLE
            binding.ivRecordingNextBtn.visibility=View.GONE

        }
        binding.ivRecordingNextBtn.setOnClickListener {
            val fragment=SelectInstrumentFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.memory_frm,fragment)
                .commit()
        }

        binding.ivRecordCancleBtn.setOnClickListener {
            binding.ivRecordBtn.visibility=View.VISIBLE
            binding.tvRecordNotice.visibility=View.GONE
            binding.ivRecordCancleBtn.visibility=View.GONE
            binding.ivRecordArrowBtn.visibility=View.GONE
            binding.ivRecordIng.visibility=View.GONE
            binding.ivRecordingNextBtn.visibility=View.GONE
        }

        binding.ivRecordArrowBtn.setOnClickListener {
            binding.ivRecordBtn.visibility=View.GONE
            binding.tvRecordNotice.visibility=View.GONE
            binding.ivRecordCancleBtn.visibility=View.GONE
            binding.ivRecordArrowBtn.visibility=View.GONE
            binding.ivRecordIng.visibility=View.GONE
            binding.ivRecordingNextBtn.visibility=View.VISIBLE


            //서버 요청메소드 호출
            sendMemoryDetailsToServer("chatcmpl-9pLFcRBq4O2dAS3yPlFosuWhkVTmS", "아 오늘 학교 갔다 왔는데 주희가 빵도 사주고, 축구도 보여줘서 너무 좋았어")

        }

        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val selectedChar = sharedPreferences.getInt("selected_char", -1)

        if (selectedChar != -1) {
            // selectedChar 값을 사용하여 작업 수행
            binding.ivBabyCharacter.setImageResource(selectedChar)
        }


        return binding.root
    }


    private fun sendMemoryDetailsToServer(id: String, text: String) {
        val service = RetrofitService.retrofit.create(DiaryIF::class.java)
        val request = Memory2Request(id, text)

        service.sendMemoryDetails(request).enqueue(object : Callback<Memory2Response> {
            override fun onResponse(call: Call<Memory2Response>, response: Response<Memory2Response>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.isSuccess) {
                        // 성공적인 응답 처리
                        Log.d("DiaryMainAnswerFragment", "성공: ${apiResponse.message}")
                        binding.tvRecordNotice.text = apiResponse.message
                    } else {
                        // 에러 처리
                        Log.e("DiaryMainAnswerFragment", "에러: ${response.errorBody()?.string()}")
                    }
                } else {
                    // 오류 응답 처리
                    Log.e("DiaryMainAnswerFragment", "응답 오류: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Memory2Response>, t: Throwable) {
                Log.d("DiaryMainAnswerFragment", "실패: ${t.message}")
            }
        })
    }
}
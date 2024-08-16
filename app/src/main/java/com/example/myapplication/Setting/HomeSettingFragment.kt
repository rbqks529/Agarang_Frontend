package com.example.myapplication.Setting

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.myapplication.ChangeChar2Fragment
import com.example.myapplication.Data.Response.HomeSettingResponse
import com.example.myapplication.Data.Response.Result
import com.example.myapplication.R
import com.example.myapplication.Retrofit.HomeIF
import com.example.myapplication.Retrofit.RetrofitService
import androidx.fragment.app.activityViewModels
import com.example.myapplication.SharedViewModel
import com.example.myapplication.databinding.FragmentHomeSettingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeSettingFragment : Fragment() {
    lateinit var binding: FragmentHomeSettingBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeSettingBinding.inflate(inflater,container,false)


        binding.ivChildInfoPlus.setOnClickListener {
            val fragment=ChildInfoChangeFragment()
            val transaction=parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frm,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.ivFamilyInfoPlus.setOnClickListener {
            val fragment=FamilyInfoFragment()
            val transaction=parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frm,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.ivCharInfoPlus.setOnClickListener {
            val fragmentChangChar= ChangeCharFragment() //일단 후기로 옮김
            val transaction=parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frm,fragmentChangChar)
                .commit()
        }

        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val selectedChar = sharedPreferences.getInt("selected_char", -1)

        if (selectedChar != -1) {
            // selectedChar 값을 사용하여 작업 수행
            binding.sivProperty.setImageResource(selectedChar)
        }
        return binding.root
    }

    private fun fetchBabyInfo() {
        val service = RetrofitService.createRetrofit(requireContext()).create(HomeIF::class.java)

        service.getSettingData().enqueue(object : Callback<HomeSettingResponse> {
            override fun onResponse(
                call: Call<HomeSettingResponse>,
                response: Response<HomeSettingResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    if (apiResponse != null && apiResponse.isSuccess) {
                        updateUI(apiResponse.result)
                    } else {
                        // 에러 처리
                        Log.e("오류", "API 요청이 성공하지 못했습니다: ${apiResponse?.message}")
                    }
                } else {
                    // 오류 응답 처리
                    val errorBody = response.errorBody()?.string()
                    Log.e("오류", "Response error: $errorBody")
                }
                /*if (response.isSuccessful) {
                    val babyInfo = response.body()
                    if (babyInfo != null) {
                        binding.tvProfileName.text = babyInfo.babyName
                        binding.tvProfileDday.text = "D-${babyInfo.dday}"
                        binding.tvProfileDueDate.text = babyInfo.dueDate
                    }
                } else {
                    // 오류 처리
                    binding.tvProfileDday.text = "Error loading data"
                }*/
            }

            override fun onFailure(call: Call<HomeSettingResponse>, t: Throwable) {
                // 네트워크 오류 또는 다른 문제 처리
                binding.tvProfileDday.text = "Error: ${t.message}"
            }
        })
    }

    private fun updateUI(result: HomeSettingResponse.Result){
        binding.tvProfileName.text = result.babyName
        binding.tvProfileDday.text = "D-${result.dday}"
        binding.tvProfileDueDate.text = result.dueDate
    }


    /*private fun infoinit() {

        //baby name
        sharedViewModel.babyName.observe(viewLifecycleOwner) { name ->
            binding.tvProfileName.text = name ?: "아깽이"
        }
        //d-day
        val d_day="DAY"
        val d_dayText=getString(R.string.d_day,d_day)
        binding.tvProfileDday.text=d_dayText
        //due date
        sharedViewModel.dueDate.observe(viewLifecycleOwner) { date ->
            binding.tvProfileDueDate.text = date?: "정보를 입력해주세요."

            if (date != null) {
                try {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val birthDate = dateFormat.parse(date)

                    if (birthDate != null) {
                        val daysUntilBirthDate = calculateDaysUntilDate(birthDate)
                        if(daysUntilBirthDate==0){
                            binding.tvProfileDday.text = "D-DAY"
                        }else if (daysUntilBirthDate>0){
                            binding.tvProfileDday.text = "D- $daysUntilBirthDate"
                            sharedViewModel.setBabyDDay(daysUntilBirthDate)
                        }
                    } else {
                        binding.tvProfileDday.text = "Invalid date format"
                    }
                } catch (e: Exception) {
                    binding.tvProfileDday.text = "Error parsing date"
                }
            } else {
                binding.tvProfileDday.text = "No birth date provided"
            }

        }

        //version init
        val currentVersion = "1.2.0"
        val latestVersion="1.5.0"
        val versionInfoText=getString(R.string.version_info, currentVersion, latestVersion)
        binding.tvVersionInfo.text=versionInfoText
    }

    private fun calculateDaysUntilDate(targetDate: Date): Int {
        val today = Calendar.getInstance().time
        val diffInMillis = targetDate.time - today.time
        val diffInDays = (diffInMillis / (1000 * 60 * 60 * 24)).toInt()
        return diffInDays
    }*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedImageResourceId = arguments?.getInt("selected_char") ?: return
        val imageView: ImageView = view.findViewById(R.id.siv_property)
        imageView.setImageResource(selectedImageResourceId)


        fetchBabyInfo()
    }
}
package com.example.myapplication.Setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapplication.Data.Response.HomeChildResponse
import com.example.myapplication.Data.Response.HomeSettingResponse
import com.example.myapplication.R
import com.example.myapplication.Retrofit.HomeIF
import com.example.myapplication.Retrofit.RetrofitService
import com.example.myapplication.SharedViewModel
import com.example.myapplication.databinding.FragmentChildInfoChangeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChildInfoChangeFragment : Fragment(), CalendarFragment.OnDateSelectedListener {
    lateinit var binding: FragmentChildInfoChangeBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChildInfoChangeBinding.inflate(inflater, container, false)

        fetchBabyInfoFromServer()

        binding.btnNameCancle.setOnClickListener {
            binding.etBirthName.setText("")
        }

        binding.icCancleBtn.setOnClickListener {
            val currentText = binding.etWeight.text.toString()
            val newText = currentText.replace(Regex("\\d+(\\.\\d+)?\\s*"), "")
            binding.etWeight.setText(newText)
        }

        binding.ivIcCalendar.setOnClickListener {
            val calendarFragment = CalendarFragment()
            calendarFragment.setTargetFragment(this, 0)
            calendarFragment.show(parentFragmentManager, "datePicker")
        }

        binding.ivCancleBox.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.ivOkBox.setOnClickListener {
            saveData()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeSettingFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.etWeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            /*override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && !s.toString().endsWith("kg")) {
                    binding.etWeight.setText("$s kg")
                    binding.etWeight.setSelection(s.length)
                }
            }*/
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && !s.toString().endsWith("kg")) {
                    val updatedText = "$s kg"
                    binding.etWeight.setText(updatedText)
                    binding.etWeight.setSelection(updatedText.length - 3) // 커서를 kg 앞에 위치
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return binding.root
    }

    private fun fetchBabyInfoFromServer() {
        val service = RetrofitService.createRetrofit(requireContext()).create(HomeIF::class.java)

        service.getChildInfoData().enqueue(object : Callback<HomeChildResponse> {
            override fun onResponse(
                call: Call<HomeChildResponse>,
                response: Response<HomeChildResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    if (apiResponse != null && apiResponse.isSuccess) {
                        updateUI(apiResponse.result)
                    } else {
                        // 에러 처리
                        Log.e("오류", "API 요청이 성공하지 못했습니다: ${apiResponse?.message}")
                        Toast.makeText(context, "서버 오류: ${apiResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // 오류 응답 처리
                    val errorBody = response.errorBody()?.string()
                    Log.e("오류", "Response error: $errorBody")
                    Toast.makeText(context, "응답 오류: $errorBody", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<HomeChildResponse>, t: Throwable) {
                Log.e("오류", "네트워크 오류: ${t.message}")
                Toast.makeText(context, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        //예시 데이터
        val babyInfo = mapOf(
            "babyName" to "주여니",
            "dueDate" to "2025-07-04",
            "weight" to "2.5"
        )

    }

    private fun updateUI(babyInfo: HomeChildResponse.Result) {
        val babyName = babyInfo.babyName
        val dueDate = babyInfo.dueDate
        val weight = babyInfo.weight.toString()

        if (!babyName.isNullOrEmpty()) {
            binding.tvBirthName.visibility = View.VISIBLE
            binding.etBirthName.visibility = View.GONE
            binding.tvBirthName.text = babyName
        } else {
            binding.tvBirthName.visibility = View.GONE
            binding.etBirthName.visibility = View.VISIBLE
        }

        if (!dueDate.isNullOrEmpty()) {
            binding.tvBirthDate.text = dueDate
        }

        if (!weight.isNullOrEmpty()) {
            binding.etWeight.setText("$weight kg")
        }
    }

    override fun onDateSelected(date: String) {
        binding.tvBirthDate.text = date
    }

    /*private fun saveData() {
        sharedViewModel.setBabyName(binding.etBirthName.text.toString())
        sharedViewModel.setDueDate(binding.tvBirthDate.text.toString())
        sharedViewModel.setBabyWeight(binding.etWeight.text.toString().replace(" kg", ""))
    }*/
    private fun saveData() {
        val babyName = if (binding.tvBirthName.visibility == View.VISIBLE) {
            binding.tvBirthName.text.toString()
        } else {
            binding.etBirthName.text.toString()
        }

        sharedViewModel.setBabyName(babyName)
        sharedViewModel.setDueDate(binding.tvBirthDate.text.toString())
        sharedViewModel.setBabyWeight(binding.etWeight.text.toString().replace(" kg", ""))
    }
}

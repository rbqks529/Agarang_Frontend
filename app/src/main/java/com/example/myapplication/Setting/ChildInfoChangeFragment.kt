package com.example.myapplication.Setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myapplication.R
import com.example.myapplication.SharedViewModel
import com.example.myapplication.databinding.FragmentChildInfoChangeBinding

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

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && !s.toString().endsWith("kg")) {
                    binding.etWeight.setText("$s kg")
                    binding.etWeight.setSelection(s.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return binding.root
    }

    private fun fetchBabyInfoFromServer() {
        //예시 데이터
        val babyInfo = mapOf(
            "babyName" to "태명",
            "dueDate" to "2025-07-04",
            "weight" to "2.5"
        )

        updateUI(babyInfo)
    }

    private fun updateUI(babyInfo: Map<String, String>) {
        val babyName = babyInfo["babyName"]
        val dueDate = babyInfo["dueDate"]
        val weight = babyInfo["weight"]

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

    private fun saveData() {
        sharedViewModel.setBabyName(binding.etBirthName.text.toString())
        sharedViewModel.setDueDate(binding.tvBirthDate.text.toString())
        sharedViewModel.setBabyWeight(binding.etWeight.text.toString().replace(" kg", ""))
    }
}

package com.example.myapplication.Setting

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentChildInfoChangeBinding

class ChildInfoChangeFragment : Fragment(), CalendarFragment.OnDateSelectedListener {
    lateinit var binding: FragmentChildInfoChangeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChildInfoChangeBinding.inflate(inflater, container, false)
        init()

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

            val newFragment = HomeSettingFragment()
            val bundle = Bundle().apply {
                val sharedPreferences = requireActivity().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
                val birthName = sharedPreferences.getString("birthName", "")
                val birthDate = sharedPreferences.getString("birthDate", "")
                putString("birthName", birthName)
                putString("birthDate", birthDate)
            }
            newFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, newFragment)
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


    override fun onDateSelected(date: String) {
        binding.tvBirthDate.text = date
    }

    private fun saveData() {
        val sharedPreferences = requireActivity().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("birthName", binding.etBirthName.text.toString())
            putString("birthDate", binding.tvBirthDate.text.toString())
            putString("weight", binding.etWeight.text.toString().replace(" kg",""))
            apply()
        }
    }

    private fun init() {
        // 초기화 작업 수행
        val sharedPreferences = requireActivity().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val birthName = sharedPreferences.getString("birthName", "")
        val birthDate = sharedPreferences.getString("birthDate", "")
        val weight = sharedPreferences.getString("weight", "")

        binding.etBirthName.setText(birthName)
        binding.tvBirthDate.text = birthDate
        binding.etWeight.setText(if (weight.isNullOrEmpty()) "" else "$weight kg")


    }
}

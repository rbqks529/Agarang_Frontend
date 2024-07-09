package com.example.myapplication.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCalendarBinding

class CalendarFragment : DialogFragment() {

    lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        binding.btnOk.setOnClickListener {
            val day = binding.dpDatepicker.dayOfMonth
            val month = binding.dpDatepicker.month
            val year = binding.dpDatepicker.year
            val selectedDate = "$year-${month + 1}-$day"
            (targetFragment as? OnDateSelectedListener)?.onDateSelected(selectedDate)
            dismiss()
        }

        binding.btnCancle.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(R.drawable.style_calendar_bg)
    }

    interface OnDateSelectedListener {
        fun onDateSelected(date: String)
    }
}

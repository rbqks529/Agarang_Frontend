package com.example.myapplication.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.Setting.CalendarFragment
import com.example.myapplication.databinding.FragmentDueDateBinding

class DueDateFragment : Fragment(),CalendarFragment.OnDateSelectedListener {
    lateinit var binding:FragmentDueDateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDueDateBinding.inflate(inflater,container,false)
        binding.ivIcCalendar.setOnClickListener {
            val calendarFragment = CalendarFragment()
            calendarFragment.setTargetFragment(this, 0)
            calendarFragment.show(parentFragmentManager, "datePicker")
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onDateSelected(date:String){
        binding.etDueDate.setText("  "+date)
    }

}
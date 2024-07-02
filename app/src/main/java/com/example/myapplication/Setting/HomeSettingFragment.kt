package com.example.myapplication.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeSettingBinding

class HomeSettingFragment : Fragment() {
    lateinit var binding: FragmentHomeSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeSettingBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }

    private fun init() {
        //baby name
        val babyname="아깽이"
        val babyNameText=getString(R.string.baby_name,babyname)
        binding.tvProfileName.text=babyNameText
        //d-day
        val d_day="DAY"
        val d_dayText=getString(R.string.d_day,d_day)
        binding.tvProfileDday.text=d_dayText
        //due date
        val year = 2024
        val month = 2
        val day = 4
        val dueDateText=getString(R.string.due_date,year.toString(),month.toString(),day.toString())
        binding.tvProfileDueDate.text=dueDateText
        //version init
        val currentVersion = "1.2.0"
        val latestVersion="1.5.0"
        val versionInfoText=getString(R.string.version_info, currentVersion, latestVersion)
        binding.tvVersionInfo.text=versionInfoText
    }

}
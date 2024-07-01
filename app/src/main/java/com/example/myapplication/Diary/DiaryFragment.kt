package com.example.myapplication.Diary.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.Diary.DiaryMainTabLayoutVPAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDiaryBinding
import com.google.android.material.tabs.TabLayoutMediator


class DiaryFragment : Fragment() {

    lateinit var binding: FragmentDiaryBinding
    private val tapList = arrayListOf("즐겨찾기", "월", "일")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.vpMain.adapter = DiaryMainTabLayoutVPAdapter(requireActivity())
        TabLayoutMediator(binding.tlMain, binding.vpMain){ tab, position ->
            tab.text = tapList[position]
        }.attach()
    }

}
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
        //version init
        val currentVersion = "1.2.0"
        val latestVersion="1.5.0"
        val versionInfoText=getString(R.string.tv_version_info, currentVersion, latestVersion)
        binding.tvVersionInfo.text=versionInfoText
    }

}
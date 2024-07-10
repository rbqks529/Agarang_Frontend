package com.example.myapplication.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFamilyInfoBinding
import com.example.myapplication.databinding.FragmentHomeSettingBinding

class FamilyInfoFragment : Fragment() {
    lateinit var binding:FragmentFamilyInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFamilyInfoBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }

    private fun init() {
        //가족 공유 코드
        val familySharingCode = "DQRKS248"
        val formattedCode = getString(R.string.family_sharing_code, familySharingCode)
        binding.tvFamilySharingCode.text=formattedCode
    }

}
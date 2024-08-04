package com.example.myapplication.Login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBirthNameBinding


class BirthNameFragment : Fragment() {
    lateinit var binding:FragmentBirthNameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBirthNameBinding.inflate(inflater,container,false)

        binding.btnNext.setOnClickListener {
            val fragment=DueDateFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm,fragment) //main_frm 바꾸기
                .addToBackStack(null)
                .commit()
        }
        binding.btnNameCancle.setOnClickListener {
            Log.d("tag","click?")
            binding.etBirthName.setText("")
        }

        return binding.root
    }



}
package com.example.myapplication.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginCode1Binding


class LoginCode1Fragment : Fragment() {

    lateinit var binding: FragmentLoginCode1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginCode1Binding.inflate(inflater, container, false)

        binding.ivInputCode.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm2, LoginCode2Fragment())
                .addToBackStack(null)
                .commit()
        }

        binding.ivRegisterCode.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm2, BirthNameFragment())
                .addToBackStack(null)
                .commit()
        }


        return binding.root
    }


}
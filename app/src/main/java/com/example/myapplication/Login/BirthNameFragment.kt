package com.example.myapplication.Login

import android.os.Bundle
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
        return binding.root
    }



}
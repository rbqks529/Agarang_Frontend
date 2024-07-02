package com.example.myapplication.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentChildInfoChangeBinding


class ChildInfoChangeFragment : Fragment() {
    lateinit var binding: FragmentChildInfoChangeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentChildInfoChangeBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }

    private fun init() {
        TODO( "Not yet implemented")
    }

}
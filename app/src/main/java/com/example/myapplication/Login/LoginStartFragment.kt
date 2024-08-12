package com.example.myapplication.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginStartBinding

class LoginStartFragment : Fragment() {

    lateinit var binding: FragmentLoginStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginStartBinding.inflate(inflater, container, false)

        binding.icLoginKakao.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm2, LoginCode1Fragment())
                .addToBackStack(null)
                .commit()
        }

        binding.icLoginGoogle.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm2, LoginCode1Fragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }


}
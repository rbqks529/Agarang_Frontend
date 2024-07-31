package com.example.myapplication.Memory

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDeepQuestionBinding

class DeepQuestionFragment : Fragment() {
    private lateinit var binding:FragmentDeepQuestionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDeepQuestionBinding.inflate(inflater,container,false)
        binding.ivRecordBtn.setOnClickListener {
            binding.ivRecordBtn.visibility=View.GONE
            binding.tvRecordNotice.visibility=View.VISIBLE
            binding.ivRecordCancleBtn.visibility=View.VISIBLE
            binding.ivRecordArrowBtn.visibility=View.VISIBLE
            binding.ivRecordIng.visibility=View.VISIBLE
            binding.ivRecordingNextBtn.visibility=View.GONE

        }
        binding.ivRecordingNextBtn.setOnClickListener {
            val fragment=SelectInstrumentFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.memory_frm,fragment)
                .commit()
        }

        binding.ivRecordCancleBtn.setOnClickListener {
            binding.ivRecordBtn.visibility=View.VISIBLE
            binding.tvRecordNotice.visibility=View.GONE
            binding.ivRecordCancleBtn.visibility=View.GONE
            binding.ivRecordArrowBtn.visibility=View.GONE
            binding.ivRecordIng.visibility=View.GONE
            binding.ivRecordingNextBtn.visibility=View.GONE
        }

        binding.ivRecordArrowBtn.setOnClickListener {
            binding.ivRecordBtn.visibility=View.GONE
            binding.tvRecordNotice.visibility=View.GONE
            binding.ivRecordCancleBtn.visibility=View.GONE
            binding.ivRecordArrowBtn.visibility=View.GONE
            binding.ivRecordIng.visibility=View.GONE
            binding.ivRecordingNextBtn.visibility=View.VISIBLE
        }

        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val selectedChar = sharedPreferences.getInt("selected_char", -1)

        if (selectedChar != -1) {
            // selectedChar 값을 사용하여 작업 수행
            binding.ivBabyCharacter.setImageResource(selectedChar)
        }
        return binding.root
    }
}
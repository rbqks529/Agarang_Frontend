package com.example.myapplication.Memory

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSelectSpeedBinding

class SelectSpeedFragment : Fragment() {

    private lateinit var binding: FragmentSelectSpeedBinding
    private var selectedSpeed: FrameLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSelectSpeedBinding.inflate(inflater, container, false)
        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val selectedChar = sharedPreferences.getInt("selected_char", -1)

        if (selectedChar != -1) {
            // selectedChar 값을 사용하여 작업 수행
            binding.ivBabyCharacter.setImageResource(selectedChar)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpeedSelection()
    }

    private fun setupSpeedSelection() {
        val speedOptions = listOf(binding.flSpeedFast, binding.flSpeedMedium, binding.flSpeedSlow)

        speedOptions.forEach { frameLayout ->
            frameLayout.setOnClickListener {
                updateSelection(frameLayout)
            }
        }
    }

    private fun updateSelection(selectedFrame: FrameLayout) {
        // 이전 선택 초기화
        selectedSpeed?.let { resetSelection(it) }

        // 새로운 선택 적용
        when (selectedFrame.id) {
            R.id.fl_speed_fast -> applySelection(binding.flSpeedFast, binding.backgroundSelected, binding.genreOption)
            R.id.fl_speed_medium -> applySelection(binding.flSpeedMedium, binding.backgroundSelected2, binding.genreOption2)
            R.id.fl_speed_slow -> applySelection(binding.flSpeedSlow, binding.backgroundSelected3, binding.genreOption3)
        }

        selectedSpeed = selectedFrame
    }

    private fun applySelection(frameLayout: FrameLayout, backgroundSelected: ImageView, textView: TextView) {
        backgroundSelected.visibility = View.VISIBLE
        textView.setTextColor(Color.parseColor("#EB5F2A"))
    }

    private fun resetSelection(frameLayout: FrameLayout) {
        when (frameLayout.id) {
            R.id.fl_speed_fast -> {
                binding.backgroundSelected.visibility = View.GONE
                binding.genreOption.setTextColor(Color.parseColor("#787878"))
            }
            R.id.fl_speed_medium -> {
                binding.backgroundSelected2.visibility = View.GONE
                binding.genreOption2.setTextColor(Color.parseColor("#787878"))
            }
            R.id.fl_speed_slow -> {
                binding.backgroundSelected3.visibility = View.GONE
                binding.genreOption3.setTextColor(Color.parseColor("#787878"))
            }
        }
    }
}
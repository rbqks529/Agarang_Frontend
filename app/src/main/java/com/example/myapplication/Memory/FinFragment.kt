package com.example.myapplication.Memory

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
import com.example.myapplication.databinding.FragmentFinBinding

class FinFragment : Fragment() {

    private lateinit var binding: FragmentFinBinding
    private var selectedOption: FrameLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOptionSelection()
    }

    private fun setupOptionSelection() {
        val options = listOf(binding.flFinYes, binding.flFinNo)

        options.forEach { frameLayout ->
            frameLayout.setOnClickListener {
                updateSelection(frameLayout)
            }
        }
    }

    private fun updateSelection(selectedFrame: FrameLayout) {
        // 이전 선택 초기화
        selectedOption?.let { resetSelection(it) }

        // 새로운 선택 적용
        when (selectedFrame.id) {
            R.id.fl_fin_yes -> applySelection(binding.flFinYes, binding.backgroundSelected, binding.genreOption)
            R.id.fl_fin_no -> applySelection(binding.flFinNo, binding.backgroundSelected2, binding.genreOption2)
        }

        selectedOption = selectedFrame
    }

    private fun applySelection(frameLayout: FrameLayout, backgroundSelected: ImageView, textView: TextView) {
        backgroundSelected.visibility = View.VISIBLE
        textView.setTextColor(Color.parseColor("#EB5F2A"))
    }

    private fun resetSelection(frameLayout: FrameLayout) {
        when (frameLayout.id) {
            R.id.fl_fin_yes -> {
                binding.backgroundSelected.visibility = View.GONE
                binding.genreOption.setTextColor(Color.parseColor("#787878"))
            }
            R.id.fl_fin_no -> {
                binding.backgroundSelected2.visibility = View.GONE
                binding.genreOption2.setTextColor(Color.parseColor("#787878"))
            }
        }
    }
}
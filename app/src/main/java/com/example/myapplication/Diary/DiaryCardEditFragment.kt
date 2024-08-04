package com.example.myapplication.Diary

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDiaryCardEditBinding


class DiaryCardEditFragment : Fragment() {

    private lateinit var binding: FragmentDiaryCardEditBinding
    private lateinit var item: DiaryMainDayData
    private var position: Int = -1

    companion object {
        fun newInstance(item: DiaryMainDayData, position: Int): DiaryCardEditFragment {
            val fragment = DiaryCardEditFragment()
            val args = Bundle().apply {
                putSerializable("item", item)
                putInt("position", position)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getSerializable("item") as DiaryMainDayData
            position = it.getInt("position")
        }
    }

    interface OnEditCompleteListener {
        fun onEditComplete(position: Int, editedItem: DiaryMainDayData)
    }

    private var editCompleteListener: OnEditCompleteListener? = null

    fun setOnEditCompleteListener(listener: OnEditCompleteListener) {
        this.editCompleteListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiaryCardEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 기존 데이터 표시
        /*binding.ivCardEditImage.setImageResource(item.imageResId)*/
        context?.let {
            Glide.with(it)
                .load(item.imageResId)
                .centerCrop()
                .into(binding.ivCardEditImage)
        }


        binding.tvDiaryCardEditContent.setText(item.content)

        binding.tvDiaryCardEditContent.requestFocus()

        // 완료 버튼 클릭 리스너
        binding.tvDiaryCardEditComplete.setOnClickListener {
            val editedContent = binding.tvDiaryCardEditContent.text.toString()
            val editedItem = item.copy(content = editedContent)
            editCompleteListener?.onEditComplete(position, editedItem)

            // 이전 화면으로 돌아가기
            parentFragmentManager.popBackStack()
        }
    }
}
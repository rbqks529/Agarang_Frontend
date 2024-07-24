package com.example.myapplication.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiaryCardEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 기존 데이터 표시
        binding.ivCardEditImage.setImageResource(item.imageResId)
        binding.tvDiaryCardEditContent.setText(item.content)

        // 완료 버튼 클릭 리스너
        binding.tvDiaryCardEditComplete.setOnClickListener {


            // 이전 화면으로 돌아가기
            parentFragmentManager.popBackStack()
        }
    }
}
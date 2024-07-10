package com.example.myapplication.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDiaryMainCardBinding


class DiaryMainCardFragment : Fragment() {
    private lateinit var binding: FragmentDiaryMainCardBinding
    private lateinit var dateAdapter: DateAdapter
    private lateinit var cardAdapter: CardviewAdapter
    private var diaryDataList: List<DiaryMainDayData> = listOf()
    private var currentPosition = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiaryMainCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val data = it.getSerializable("data") as? List<DiaryMainDayData>
            val position = it.getInt("position", 0)
            if (data != null) {
                setData(data, position)
            }
        }
    }

    private fun setupRecyclerViews() {
        // Date RecyclerView
        dateAdapter = DateAdapter(diaryDataList) { position ->
            currentPosition = position
            binding.rvDiaryCardView.smoothScrollToPosition(position)
        }
        binding.rvDiaryDate.apply {
            adapter = dateAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    currentPosition = layoutManager.findFirstVisibleItemPosition()
                    /*binding.rvDiaryCardView.scrollToPosition(currentPosition)*/
                }
            })
        }

        // Card RecyclerView
        cardAdapter = CardviewAdapter(diaryDataList)
        binding.rvDiaryCardView.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    currentPosition = layoutManager.findFirstVisibleItemPosition()
                    binding.rvDiaryDate.smoothScrollToPosition(currentPosition)
                }
            })
        }

        // 초기 선택 위치 설정
        binding.rvDiaryDate.scrollToPosition(currentPosition)
        binding.rvDiaryCardView.scrollToPosition(currentPosition)
    }

    fun setData(data: List<DiaryMainDayData>, initialPosition: Int) {
        diaryDataList = data
        currentPosition = initialPosition
        setupRecyclerViews()
    }
}
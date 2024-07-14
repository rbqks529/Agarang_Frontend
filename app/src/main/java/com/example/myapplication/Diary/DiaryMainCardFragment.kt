package com.example.myapplication.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDiaryMainCardBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DiaryMainCardFragment : Fragment() {
    private lateinit var binding: FragmentDiaryMainCardBinding
    private lateinit var dateAdapter: DateAdapter
    private lateinit var cardAdapter: CardviewAdapter
    private var diaryDataList: MutableList<DiaryMainDayData> = mutableListOf()
    private var currentPosition = 0
    private var scrollJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiaryMainCardBinding.inflate(inflater, container, false)

        binding.ivDiaryPrevious.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val data = it.getSerializable("data") as? ArrayList<DiaryMainDayData>
            val position = it.getInt("position", 0)

            if (data != null) {
                setData(data, position)
            }
        }
    }

    private fun setupRecyclerViews() {
        dateAdapter = DateAdapter(diaryDataList) { position ->
            scrollToPosition(position)
        }
        binding.rvDiaryDate.apply {
            adapter = dateAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        cardAdapter = CardviewAdapter(diaryDataList)
        binding.rvDiaryCardView.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            // PagerSnapHelper 추가
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)

            // 패딩 설정으로 첫 번째와 마지막 아이템도 중앙에 올 수 있게 함
            val padding = resources.displayMetrics.widthPixels / 2 -
                    resources.getDimensionPixelSize(R.dimen.card_item_width) / 2
            setPadding(padding, 0, padding, 0)
            clipToPadding = false

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val centerView = snapHelper.findSnapView(layoutManager)
                        val position = centerView?.let { layoutManager?.getPosition(it) } ?: RecyclerView.NO_POSITION
                        if (position != RecyclerView.NO_POSITION && position != currentPosition) {
                            currentPosition = position
                            updateDateSelection(currentPosition)
                        }
                    }
                }
            })
        }

        scrollToPosition(currentPosition)
    }

    private fun scrollToPosition(position: Int) {
        scrollJob?.cancel()
        scrollJob = MainScope().launch {
            delay(100) // debounce

            // CardView를 중앙으로 스크롤
            val layoutManager = binding.rvDiaryCardView.layoutManager as LinearLayoutManager
            val itemView = layoutManager.findViewByPosition(position)
            if (itemView == null) {
                // View가 아직 생성되지 않았을 경우, 예상 위치로 스크롤
                /*val estimatedItemWidth = resources.getDimensionPixelSize(R.dimen.card_item_width) + 24
                val screenWidth = resources.displayMetrics.widthPixels
                val offset = (screenWidth - estimatedItemWidth) / 2*/
                layoutManager.scrollToPosition(position)
            }


            // 날짜 RecyclerView를 중앙으로 스크롤 (기존 코드)
            val dateLayoutManager = binding.rvDiaryDate.layoutManager as LinearLayoutManager
            val smoothScroller = CenterSmoothScroller(requireContext())
            smoothScroller.targetPosition = position
            dateLayoutManager.startSmoothScroll(smoothScroller)
            dateAdapter.updateSelectedPosition(position)
        }
    }

    private fun updateDateSelection(position: Int) {
        dateAdapter.updateSelectedPosition(position)

        val layoutManager = binding.rvDiaryDate.layoutManager as LinearLayoutManager
        val smoothScroller = CenterSmoothScroller(requireContext())
        smoothScroller.targetPosition = position
        layoutManager.startSmoothScroll(smoothScroller)
    }

    fun setData(data: List<DiaryMainDayData>, initialPosition: Int) {
        diaryDataList = data.toMutableList()
        currentPosition = initialPosition
        setupRecyclerViews()
    }
}
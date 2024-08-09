package com.example.myapplication.Diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Request.bookmarkSetRequest
import com.example.myapplication.Data.Response.BookmarkSetResult
import com.example.myapplication.R
import com.example.myapplication.Retrofit.DiaryIF
import com.example.myapplication.Retrofit.RetrofitService
import com.example.myapplication.databinding.FragmentDiaryMainCardBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class DiaryMainCardFragment : Fragment() {
    private lateinit var binding: FragmentDiaryMainCardBinding
    private lateinit var dateAdapter: DateAdapter
    private lateinit var cardAdapter: CardViewAdapter
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
        val currentCalendar = Calendar.getInstance()
        dateAdapter = DateAdapter(
            currentCalendar.get(Calendar.YEAR),
            currentCalendar.get(Calendar.MONTH) + 1,
            diaryDataList
        ) { position ->
            scrollToPosition(position)
        }
        binding.rvDiaryDate.apply {
            adapter = dateAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        cardAdapter = CardViewAdapter(diaryDataList, { deletedItem ->
            // 항목이 삭제되었을 때 호출되는 콜백
            updateDateAdapterAfterDeletion(deletedItem)
        }, { itemId ->
            sendBookmarkRequest(itemId)
        })
        binding.rvDiaryCardView.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)

            val padding = resources.displayMetrics.widthPixels / 2 -
                    resources.getDimensionPixelSize(R.dimen.card_item_width) / 2
            setPadding(padding - 12, 0, padding - 12, 0)
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

    private fun sendBookmarkRequest(itemId: Long) {

        val apiService = RetrofitService.retrofit.create(DiaryIF::class.java)
        val request = bookmarkSetRequest(memoryId = itemId)
        apiService.sendBookmarkSet(request).enqueue(object : Callback<BookmarkSetResult> {
            override fun onResponse(call: Call<BookmarkSetResult>, response: Response<BookmarkSetResult>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null && result.isSuccess) {
                        Log.d("Bookmark", "북마크 업데이트 성공")
                    } else {
                        Log.e("Bookmark", "북마크 업데이트 실패: ${response.code()}")
                    }
                } else {
                    Log.e("Bookmark", "북마크 업데이트 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BookmarkSetResult>, t: Throwable) {
                Log.e("Bookmark", "서버 통신 실패", t)
            }
        })

    }

    private fun scrollToPosition(position: Int) {
        scrollJob?.cancel()
        scrollJob = MainScope().launch {
            delay(100) // debounce

            if (position != RecyclerView.NO_POSITION && position < diaryDataList.size) {
                currentPosition = position

                // CardView를 중앙으로 스크롤
                val cardLayoutManager = binding.rvDiaryCardView.layoutManager as LinearLayoutManager
                cardLayoutManager.scrollToPositionWithOffset(position, 0)

                // 날짜 RecyclerView를 중앙으로 스크롤
                val dateLayoutManager = binding.rvDiaryDate.layoutManager as LinearLayoutManager
                val smoothScroller = CenterSmoothScroller(requireContext())
                smoothScroller.targetPosition = diaryDataList[position].day - 1
                dateLayoutManager.startSmoothScroll(smoothScroller)

                // DateAdapter의 선택된 위치 업데이트
                dateAdapter.updateSelectedPosition(diaryDataList[position].day - 1)
            } else {
                // 모든 아이템이 삭제된 경우
                currentPosition = RecyclerView.NO_POSITION
                dateAdapter.updateSelectedPosition(RecyclerView.NO_POSITION)
            }
        }
    }

    private fun updateDateAdapterAfterDeletion(deletedItem: DiaryMainDayData) {
        // diaryDataList에서 삭제된 항목 제거
        diaryDataList.remove(deletedItem)

        // DateAdapter 업데이트
        dateAdapter.updateItems(diaryDataList)

        // 현재 위치가 유효한지 확인하고 필요하다면 조정
        if (currentPosition >= diaryDataList.size) {
            currentPosition = diaryDataList.size - 1
        }

        // DateAdapter의 선택된 위치 업데이트
        if (diaryDataList.isNotEmpty()) {
            val day = diaryDataList[currentPosition].day
            dateAdapter.updateSelectedPosition(day - 1)
            scrollToPosition(currentPosition)
        } else {
            dateAdapter.updateSelectedPosition(RecyclerView.NO_POSITION)
        }

        // CardView의 위치 조정
        if (diaryDataList.isNotEmpty()) {
            binding.rvDiaryCardView.scrollToPosition(currentPosition)
        }
    }

    private fun updateDateSelection(position: Int) {
        val day = diaryDataList[position].day
        dateAdapter.updateSelectedPosition(day - 1)

        val layoutManager = binding.rvDiaryDate.layoutManager as LinearLayoutManager
        val smoothScroller = CenterSmoothScroller(requireContext())
        smoothScroller.targetPosition = day - 1
        layoutManager.startSmoothScroll(smoothScroller)
    }

    fun setData(data: List<DiaryMainDayData>, initialPosition: Int) {
        diaryDataList = data.toMutableList()
        currentPosition = initialPosition
        setupRecyclerViews()
    }

}
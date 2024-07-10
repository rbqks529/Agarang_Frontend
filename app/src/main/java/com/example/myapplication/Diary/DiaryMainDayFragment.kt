package com.example.myapplication.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDiaryMainDayBinding


class DiaryMainDayFragment : Fragment() {

    lateinit var binding: FragmentDiaryMainDayBinding
    private var DiaryDayAdapter : DiaryDayAdapter?= null
    private var DiaryDayitemList : ArrayList<DiaryMainDayData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDiaryMainDayBinding.inflate(inflater, container, false)

        //데이터 생성
        initData()
        //RecyclerView 생성
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val spanCount = 3 // 열의 수
        DiaryDayAdapter = DiaryDayAdapter(requireContext(), DiaryDayitemList)
        binding.rvDiaryDay.adapter = DiaryDayAdapter
        binding.rvDiaryDay.layoutManager = GridLayoutManager(context, spanCount)

        // 아이템 크기 고정
        binding.rvDiaryDay.addItemDecoration(SquareItemDecoration(spanCount))

        DiaryDayAdapter?.setOnItemClickListener(object : DiaryDayAdapter.OnItemClickListener {
            override fun onItemClick(item: DiaryMainDayData) {
                navigateToDiaryMainCard(item)
            }
        })

        /*// 아이템 간 간격 설정
        val spacingHorizontal = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        val spacingVertical = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        binding.rvDiary.addItemDecoration(GridSpacingItemDecoration(spanCount, spacingHorizontal, spacingVertical))*/
    }

    private fun navigateToDiaryMainCard(item: DiaryMainDayData) {
        val fragment = DiaryMainCardFragment()
        val bundle = Bundle().apply {
            putSerializable("data", ArrayList(DiaryDayitemList))
            putInt("position", DiaryDayitemList.indexOf(item))
        }
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initData() {
        DiaryDayitemList.addAll(
            arrayListOf(
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 1"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 2"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 4"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 5"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 6"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 7"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 8"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 9"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 10"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 11"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 12"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 13"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 14"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 15"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 16"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 17"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 18"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 19"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 20"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 21"),
                DiaryMainDayData(R.drawable.post_sample, "2024 / 5 / 22")
            )
        )

    }

}

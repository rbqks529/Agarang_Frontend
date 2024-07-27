package com.example.myapplication.Memory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSelectInstrumentBinding


class SelectInstrumentFragment : Fragment(), InstrumentAdapter.OnItemClickListener {

    private var _binding: FragmentSelectInstrumentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSelectInstrumentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val instruments = listOf(
            InstrumentData(R.drawable.ic_piano, "피아노", false),
            InstrumentData(R.drawable.ic_flute, "플룻"),
            InstrumentData(R.drawable.ic_guitar, "기타"),
            InstrumentData(R.drawable.ic_violine, "바이올린"),
            InstrumentData(R.drawable.ic_trumpet, "트럼펫"),
            InstrumentData(R.drawable.ic_xylophone, "실로폰", false),
            InstrumentData(R.drawable.ic_harp, "하프", false),
            InstrumentData(R.drawable.ic_cello, "첼로"),
            InstrumentData(R.drawable.ic_saxophone, "색소폰")
        )


//수정(클릭리스터 여기에)
        val adapter = InstrumentAdapter(instruments, this)
        binding.rvInstruments.layoutManager = GridLayoutManager(context, 3)
        binding.rvInstruments.adapter = adapter

    }
    override fun onItemClick(instrument: InstrumentData) {
        // 모든 악기의 선택 상태를 해제
        val instruments = (binding.rvInstruments.adapter as InstrumentAdapter).instruments
        instruments.forEach { it.isSelected = false }
        // 선택된 악기만 선택 상태로 변경
        instrument.isSelected = true
        binding.rvInstruments.adapter?.notifyDataSetChanged()

        // 프래그먼트 전환
        val fragment = SelectGenreFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.memory_frm, fragment)
            .addToBackStack(null)
            .commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
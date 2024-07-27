package com.example.myapplication.Memory

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSelectInstrumentBinding

class SelectInstrumentFragment : Fragment() {

    private lateinit var rvInstruments: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_select_instrument, container, false)
        rvInstruments = view.findViewById(R.id.rv_instruments)

        val imageView = view.findViewById<ImageView>(R.id.iv_baby_character)

        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val selectedChar = sharedPreferences.getInt("selected_char", -1)

        if (selectedChar != -1) {
            // selectedChar 값을 사용하여 작업 수행
            imageView.setImageResource(selectedChar)
        }

        return view
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
            InstrumentData(R.drawable.ic_saxophone, "색소폰"),
        )

        val adapter = InstrumentAdapter(instruments) { selectedInstrument ->
            // 모든 악기의 선택 상태를 해제
            instruments.forEach { it.isSelected = false }
            // 선택된 악기만 선택 상태로 변경
            selectedInstrument.isSelected = true
            rvInstruments.adapter?.notifyDataSetChanged()
        }

        rvInstruments.layoutManager = GridLayoutManager(context, 3)
        rvInstruments.adapter = adapter
    }
}
package com.example.myapplication.Memory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R

class GenreAdapter(
    private val context: Context,
    private val items: Array<String>
) : BaseAdapter() {

    // 선택된 아이템의 인덱스를 저장할 변수
    private var selectedPosition = -1

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.genre_item, parent, false)
        }

        val genreOption = view?.findViewById<TextView>(R.id.genre_option)
        val backgroundSelected = view?.findViewById<ImageView>(R.id.background_selected)

        // 아이템의 텍스트 설정
        genreOption?.text = items[position].toString()

        // 선택된 아이템이면 배경을 보이게, 아니면 감추기
        if (position == selectedPosition) {
            backgroundSelected?.visibility = View.VISIBLE
        } else {
            backgroundSelected?.visibility = View.GONE
        }

        // 아이템 클릭 이벤트 처리
        view?.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()  // 뷰 갱신
        }

        return view!!
    }
}
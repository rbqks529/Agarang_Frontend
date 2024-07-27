package com.example.myapplication.Memory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R

class MoodAdapter(
    private val context: Context,
    private val items: Array<String>,
    private val fragmentManager: FragmentManager
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
            view = inflater.inflate(R.layout.mood_item, parent, false)
        }

        val moodOption = view?.findViewById<TextView>(R.id.mood_option)
        val backgroundSelected = view?.findViewById<ImageView>(R.id.background_selected)

        // 아이템의 텍스트 설정
        moodOption?.text = items[position].toString()

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

            // SelectSpeedFragment로 전환
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.memory_frm, SelectSpeedFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view!!
    }
}
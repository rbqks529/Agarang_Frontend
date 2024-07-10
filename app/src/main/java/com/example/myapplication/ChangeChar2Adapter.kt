package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

public class ChangeChar2Adapter(val context: Context, val items: IntArray): BaseAdapter(){
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
            view = inflater.inflate(R.layout.char2_item, parent, false)
        }

        val iconImageView = view?.findViewById<ImageView>(R.id.icon_image)
        iconImageView?.setImageResource(items[position])

        return view ?: throw IllegalStateException("View should not be null")
    }

}
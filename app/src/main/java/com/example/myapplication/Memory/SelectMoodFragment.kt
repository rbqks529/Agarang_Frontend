package com.example.myapplication.Memory

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.myapplication.R

class SelectMoodFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_mood, container, false)

        val gridView = view.findViewById<GridView>(R.id.gv_mood)
        val genres = arrayOf("아름다운", "밝은", "행복한", "평화로운", "따뜻한", "활기찬","기쁜","환상적인", "사랑스러운")
        val adapter = GenreAdapter(requireContext(), genres)

        gridView.adapter = adapter

        return view
    }

}
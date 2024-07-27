package com.example.myapplication.Memory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.myapplication.R

class SelectGenreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_genre, container, false)

        val gridView = view.findViewById<GridView>(R.id.gv_genre)
        val genres = arrayOf("발라드", "팝", "재즈", "어쿠스틱", "알앤비", "일렉트로닉","락", "인디고", "힙합")
        val adapter = GenreAdapter(requireContext(), genres, requireActivity().supportFragmentManager)

        gridView.adapter = adapter

        return view
    }

}
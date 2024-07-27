package com.example.myapplication.Memory

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSelectGenreBinding

class SelectGenreFragment : Fragment() {

    lateinit var binding: FragmentSelectGenreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectGenreBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_select_genre, container, false)
        val gridView = view.findViewById<GridView>(R.id.gv_genre)
        val genres = arrayOf("발라드", "팝", "재즈", "어쿠스틱", "알앤비", "일렉트로닉","락", "인디고", "힙합")
        val adapter = GenreAdapter(requireContext(), genres, requireActivity().supportFragmentManager)

        gridView.adapter = adapter

        val imageView = view.findViewById<ImageView>(R.id.iv_baby_character)

        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val selectedChar = sharedPreferences.getInt("selected_char", -1)

        if (selectedChar != -1) {
            // selectedChar 값을 사용하여 작업 수행
            imageView.setImageResource(selectedChar)
        }

        return view
    }

}
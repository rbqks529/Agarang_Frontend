package com.example.myapplication.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.myapplication.R


class ChangeChar2Fragment : Fragment() {

    private val imageResources = intArrayOf(
        R.drawable.mouse_2,
        R.drawable.cow_2,
        R.drawable.tiger_2,
        R.drawable.rabbit_2,
        R.drawable.dragon_2,
        R.drawable.snake_2,
        R.drawable.horse_2,
        R.drawable.sheep_2,
        R.drawable.monkey_2,
        R.drawable.chick_2,
        R.drawable.dog_2,
        R.drawable.pig_2
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_char2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridView: GridView = view.findViewById(R.id.gv_change)
        val adapter = ChangeChar2Adapter(requireContext(), imageResources)
        gridView.adapter = adapter
    }


}
package com.example.myapplication.Music

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMusicBinding

class MusicFragment : Fragment() {
    lateinit var binding:FragmentMusicBinding
    private var itemList:ArrayList<MusicMainData> = arrayListOf()
    private var musicMainAdapter:MusicMainAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMusicBinding.inflate(inflater,container,false)

        itemList.add(MusicMainData(R.drawable.playlist_bookmark,"즐겨 찾기"))
        itemList.add(MusicMainData(R.drawable.playlist_all,"전체 플레이리스트"))
        itemList.add(MusicMainData(R.drawable.playlist_alone,"혼자만의 시간을 가지는"))
        itemList.add(MusicMainData(R.drawable.playlist_day,"하루를 정리하며 듣는"))
        itemList.add(MusicMainData(R.drawable.playlist_exercise,"운동하며 듣는"))
        itemList.add(MusicMainData(R.drawable.playlist_fetal_movement,"태동이 느껴질 때 듣는"))
        itemList.add(MusicMainData(R.drawable.playlist_morning,"아침을 시작하며 듣는"))
        itemList.add(MusicMainData(R.drawable.playlist_day,"마음을 편안하게 하는"))

        recycler()
        return binding.root
    }

    private fun recycler() {
        musicMainAdapter=MusicMainAdapter(itemList,requireActivity())
        binding.rvMusic.adapter=musicMainAdapter
        val spanCount=2
        binding.rvMusic.layoutManager=GridLayoutManager(requireContext(),spanCount)
    }

}
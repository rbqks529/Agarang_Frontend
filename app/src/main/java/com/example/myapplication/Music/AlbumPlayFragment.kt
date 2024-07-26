package com.example.myapplication.Music

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAlbumPlayBinding
import com.example.myapplication.databinding.FragmentMusicAlbumBinding

class AlbumPlayFragment : Fragment() {
    lateinit var binding:FragmentAlbumPlayBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAlbumPlayBinding.inflate(inflater,container,false)
        return binding.root
    }

}
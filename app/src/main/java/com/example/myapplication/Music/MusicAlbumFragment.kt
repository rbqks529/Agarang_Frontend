package com.example.myapplication.Music

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMusicAlbumBinding

class MusicAlbumFragment : Fragment() {
    private lateinit var binding: FragmentMusicAlbumBinding
    private val itemList: ArrayList<MusicAlbumData> = arrayListOf()
    private var musicAlbumAdapter: MusicAlbumAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicAlbumBinding.inflate(inflater, container, false)

        populateItemList()
        setupRecyclerView()

        return binding.root
    }

    private fun populateItemList() {
        itemList.apply {
            add(MusicAlbumData(R.drawable.music_image_sample, "즐겨 찾기", "신남", "신남"))
            add(MusicAlbumData(R.drawable.music_image_sample, "운동할 때 들을 플레이리스트", "즐거움", "즐거움"))
            add(MusicAlbumData(R.drawable.music_image_sample, "플레이리스트", "산책", "산책"))
            add(MusicAlbumData(R.drawable.music_image_sample, "즐겨 찾기", "신남", "신남"))
            add(MusicAlbumData(R.drawable.music_image_sample, "운동할 때 들을 플레이리스트", "즐거움", "즐거움"))
            add(MusicAlbumData(R.drawable.music_image_sample, "플레이리스트", "산책", "산책"))
            add(MusicAlbumData(R.drawable.music_image_sample, "즐겨 찾기", "신남", "신남"))
            add(MusicAlbumData(R.drawable.music_image_sample, "운동할 때 들을 플레이리스트", "즐거움", "즐거움"))
            add(MusicAlbumData(R.drawable.music_image_sample, "플레이리스트", "산책", "산책"))
        }
    }

    private fun setupRecyclerView() {
        musicAlbumAdapter = MusicAlbumAdapter(itemList, object : MusicAlbumAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = itemList[position]
                val bundle = Bundle().apply {
                    putParcelable("music_album_data", item)
                    putParcelableArrayList("play_list", itemList)
                }
                val fragment = AlbumPlayFragment().apply {
                    arguments = bundle
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })

        binding.rvMusicAlbum.apply {
            adapter = musicAlbumAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}

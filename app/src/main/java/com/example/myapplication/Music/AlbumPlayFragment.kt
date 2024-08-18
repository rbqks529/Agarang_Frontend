package com.example.myapplication.Music

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.FragmentAlbumPlayBinding

class AlbumPlayFragment : Fragment() {
    lateinit var binding:FragmentAlbumPlayBinding
    private var itemList: ArrayList<MusicAlbumData> = arrayListOf()

    private var musicAlbumPlayAdapter: MusicPlayAdapter? = null
    private var currentTrack:MusicAlbumData?=null
    private val handler=Handler(Looper.getMainLooper())
    private var updateSeekBarkRunnable:Runnable?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAlbumPlayBinding.inflate(inflater,container,false)
        val musicAlbumData: MusicAlbumData? = arguments?.getParcelable("music_album_data")
        val playlist:ArrayList<MusicAlbumData>?=arguments?.getParcelableArrayList<MusicAlbumData>("play_list")

        musicAlbumData?.let {
            currentTrack=it
            updateUIWithTrackInfo(it)
        }
        playlist?.let{
            itemList.addAll(it)
        }

        val playlistId=arguments?.getLong("playlistId")
        musicAlbumPlayAdapter = MusicPlayAdapter(
            requireContext(),
            playlistId = playlistId!!.toLong() ,
            itemList,
            binding.rvMusicAlbum,
            object :MusicPlayAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val item=itemList[position]
                currentTrack=item
                updateUIWithTrackInfo(item)
            }
        })
        binding.rvMusicAlbum.adapter = musicAlbumPlayAdapter
        binding.rvMusicAlbum.layoutManager = LinearLayoutManager(requireContext())


        // 음악 실행되어야 함 //
        binding.ivPlayStopIc.setOnClickListener {
            musicAlbumPlayAdapter!!.playPauseMusic()
            togglePlayPause()
        }
        binding.ivPlayBackIc.setOnClickListener {
            currentTrack?.let {
                musicAlbumPlayAdapter!!.playNextTrack(it)
            }
        }
        binding.ivPlayForeIc.setOnClickListener {
            currentTrack?.let {
                musicAlbumPlayAdapter!!.playPreviousTrack(it)
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicAlbumPlayAdapter?.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        setupSeekBarUpdater()
        return binding.root
    }

    private fun togglePlayPause() {
        if(musicAlbumPlayAdapter?.isPlaying()==true){
            //musicAlbumPlayAdapter?.pauseMusic()
            binding.ivPlayStopIc.isVisible=true
            binding.ivPlayStartIc.isVisible=false

        }else{
            //musicAlbumPlayAdapter?.playMusic()
            binding.ivPlayStopIc.isVisible=false
            binding.ivPlayStartIc.isVisible=true
        }
    }

    private fun updateUIWithTrackInfo(it: MusicAlbumData) {
        Glide.with(binding.ivAlbumCover.context)
            .load(it.imageUrl)
            .into(binding.ivAlbumCover)
        binding.tvPlayMusicName.text=it.musicTitle
        binding.tvPlayMusicHashTag.text=it.musicTag1
        binding.tvPlayMusicHashTag2.text=it.musicTag2
    }

    private fun setupSeekBarUpdater() {
        updateSeekBarkRunnable=object :Runnable{
            override fun run() {
                val currentPosition=musicAlbumPlayAdapter?.getCurrentPosition()?:0
                val duration = musicAlbumPlayAdapter?.getDuration()?:0

                binding.tvMusicTime1.text=formatTime(currentPosition)
                binding.tvMusicTime2.text=formatTime(duration)
                binding.seekBar.progress=currentPosition
                binding.seekBar.max=duration
                handler.postDelayed(this,1000)
            }

        }
        updateSeekBarkRunnable?.run()
    }
    private fun formatTime(milliseconds: Int): String {
        val minutes = (milliseconds / 1000) / 60
        val seconds = (milliseconds / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 핸들러와 Runnable 제거
        updateSeekBarkRunnable?.let { handler.removeCallbacks(it) }
    }

}
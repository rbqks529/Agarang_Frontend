package com.example.myapplication.Music

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.SharedViewModel
import com.example.myapplication.databinding.FragmentAlbumPlayBinding

class AlbumPlayFragment : Fragment() {
    lateinit var binding:FragmentAlbumPlayBinding
    private var itemList: ArrayList<MusicAlbumData> = arrayListOf()

    private var musicAlbumPlayAdapter: MusicPlayAdapter? = null
    private var currentTrack:MusicAlbumData?=null
    private val handler=Handler(Looper.getMainLooper())
    private var updateSeekBarkRunnable:Runnable?=null

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("AlbumPlayFragment","one")
        binding= FragmentAlbumPlayBinding.inflate(inflater,container,false)
        val musicAlbumData: MusicAlbumData? = arguments?.getParcelable("music_album_data")
        val playlist:ArrayList<MusicAlbumData>?=arguments?.getParcelableArrayList<MusicAlbumData>("play_list")

        sharedViewModel=ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val lastPlayedTrack=loadLastPlayedTrack()
        lastPlayedTrack?.let{
            sharedViewModel.setCurrentTrack(it)
        }

        sharedViewModel.currentTrack.observe(viewLifecycleOwner, { track ->
            track?.let{
                saveLastPlayedTrack(it)
            }
        })

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
                playTrack(item)
            }
        })
        binding.rvMusicAlbum.adapter = musicAlbumPlayAdapter
        binding.rvMusicAlbum.layoutManager = LinearLayoutManager(requireContext())


        // 음악 실행되어야 함 //
        binding.ivPlayStopIc.setOnClickListener {
            musicAlbumPlayAdapter!!.playPauseMusic()
            if(musicAlbumPlayAdapter?.isPlaying()==true){
                binding.ivPlayStopIc.isVisible=true
                binding.ivPlayStartIc.isVisible=false

            }else{
                binding.ivPlayStopIc.isVisible=false
                binding.ivPlayStartIc.isVisible=true
            }
        }
        binding.ivPlayStartIc.setOnClickListener {
            musicAlbumPlayAdapter!!.playPauseMusic()
            if(musicAlbumPlayAdapter?.isPlaying()==true){
                binding.ivPlayStopIc.isVisible=true
                binding.ivPlayStartIc.isVisible=false

            }else{
                binding.ivPlayStopIc.isVisible=false
                binding.ivPlayStartIc.isVisible=true
            }
        }
        binding.ivPlayBackIc.setOnClickListener {
            currentTrack?.let {
                musicAlbumPlayAdapter!!.playNextTrack(it){
                    currentTrack=musicAlbumPlayAdapter?.getNextTrack(it)
                    currentTrack?.let{track -> updateUIWithTrackInfo(track)}
                }
            }
        }
        binding.ivPlayForeIc.setOnClickListener {
            currentTrack?.let {
                musicAlbumPlayAdapter!!.playPreviousTrack(it){
                    currentTrack=musicAlbumPlayAdapter?.getPreviousTrack(it)
                    currentTrack?.let { track -> updateUIWithTrackInfo(track) }
                }
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

        musicAlbumPlayAdapter?.playMusic(currentTrack!!.musicUrl){
            currentTrack=musicAlbumPlayAdapter?.getNextTrack(currentTrack!!)
            currentTrack?.let { track ->
                updateUIWithTrackInfo(track)
                musicAlbumPlayAdapter?.playMusic(track.musicUrl)
            }
        }

        setupSeekBarUpdater()
        return binding.root
    }

    private fun saveLastPlayedTrack(track: MusicAlbumData) {
        val sharedPreferences = context?.getSharedPreferences("MusicPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        editor?.putString("LAST_PLAYED_TITLE", track.musicTitle)
        editor?.putString("LAST_PLAYED_URL", track.musicUrl)
        editor?.putString("LAST_PLAYED_IMAGE", track.imageUrl)
        editor?.putString("LAST_PLAYED_HASH_TAG1",track.musicTag1)
        editor?.putString("LAST_PLAYED_HASH_TAG2",track.musicTag2)

        editor?.apply() // 저장
    }

    private fun loadLastPlayedTrack(): MusicAlbumData? {
        val sharedPreferences = context?.getSharedPreferences("MusicPrefs", Context.MODE_PRIVATE)

        val title = sharedPreferences?.getString("LAST_PLAYED_TITLE", null)
        val musicUrl = sharedPreferences?.getString("LAST_PLAYED_URL", null)
        val imageUrl = sharedPreferences?.getString("LAST_PLAYED_IMAGE", null)
        val tag1 = sharedPreferences?.getString("LAST_PLAYED_HASH_TAG1", null)
        val tag2 = sharedPreferences?.getString("LAST_PLAYED_HASH_TAG2", null)

        return if (title != null && musicUrl != null && imageUrl != null && tag1 != null && tag2!=null) {
            MusicAlbumData(-1, imageUrl, title, musicUrl, tag1,tag2, false)
        } else {
            null
        }
    }

    private fun playTrack(track: MusicAlbumData) {
        musicAlbumPlayAdapter?.playMusic(track.musicUrl)
        sharedViewModel.setCurrentTrack(track) // ViewModel에 현재 트랙 업데이트
        saveLastPlayedTrack(track) // SharedPreferences에 저장
    }

//    private fun togglePlayPause() {
//        if(musicAlbumPlayAdapter?.isPlaying()==true){
//            binding.ivPlayStopIc.isVisible=true
//            binding.ivPlayStartIc.isVisible=false
//
//        }else{
//            binding.ivPlayStopIc.isVisible=false
//            binding.ivPlayStartIc.isVisible=true
//        }
//    }

    private fun updateUIWithTrackInfo(it: MusicAlbumData) {
        Glide.with(binding.ivAlbumCover.context)
            .load(it.imageUrl)
            .into(binding.ivAlbumCover)
        binding.tvPlayMusicName.text=it.musicTitle
        binding.tvPlayMusicHashTag.text=it.musicTag1
        binding.tvPlayMusicHashTag2.text=it.musicTag2
        binding.ivPlayStopIc.isVisible=true
        binding.ivPlayStartIc.isVisible=false
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
package com.example.myapplication.Music

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.myapplication.Diary.DiaryDayAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.MusicItemBinding

class MusicMainAdapter(val items:ArrayList<MusicMainData>, private val fragmentActivity: FragmentActivity):RecyclerView.Adapter<MusicMainAdapter.ViewHolder>() {

    inner class ViewHolder (val binding:MusicItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: MusicMainData){
            binding.ivRvCover1.setImageResource(item.musicImgId)
            binding.tvCover1.text=item.musicContent

            if (position==0){
                binding.ivCoverHeart.visibility= View.VISIBLE
            }else{
                binding.ivCoverHeart.visibility= View.GONE
            }

            binding.ivRvCover1.setOnClickListener{
                val albumTitle = binding.tvCover1.text.toString()
                openMusicAlbumFragment(albumTitle)
            }
        }
        private fun openMusicAlbumFragment(albumTitle: String) {
            val bundle = Bundle()
            bundle.putString("albumTitle", albumTitle)

            val musicAlbumFragment = MusicAlbumFragment()
            musicAlbumFragment.arguments = bundle

            val fragmentManager: FragmentManager = fragmentActivity.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, musicAlbumFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicMainAdapter.ViewHolder {
        val binding=MusicItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
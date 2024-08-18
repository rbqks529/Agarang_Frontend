package com.example.myapplication.Music

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.AlbumMusicItemBinding

class MusicAlbumAdapter(
    private val items: ArrayList<MusicAlbumData>,
    private val itemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<MusicAlbumAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(private val binding: AlbumMusicItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MusicAlbumData) {
            Glide.with(binding.ivItemCover.context)
                .load(item.imageUrl)
                .into(binding.ivItemCover)
            binding.tvItemTitle.text = item.musicTitle
            binding.tvItemTag1.text = item.musicTag1
            binding.tvItemTag2.text = item.musicTag2

            binding.ivItemHeartEmpty.setOnClickListener {
                toggleHeart()
            }

            binding.ivItemCover.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }
            binding.tvItemTag1.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }
            binding.tvItemTag2.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }
            binding.tvItemTitle.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }

            binding.ivItemOption.setOnClickListener {
                showDeleteConfirmationDialog(itemView.context, item)
            }
        }

        private fun toggleHeart() {
            if (binding.ivItemHeartEmpty.tag == null || binding.ivItemHeartEmpty.tag == "empty") {
                binding.ivItemHeartEmpty.setImageResource(R.drawable.ic_music_heart)
                binding.ivItemHeartEmpty.tag = "filled"
            } else {
                binding.ivItemHeartEmpty.setImageResource(R.drawable.icon_heart_gray_empty)
                binding.ivItemHeartEmpty.tag = "empty"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AlbumMusicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun showDeleteConfirmationDialog(context: Context, item: MusicAlbumData) {
        Log.d("MusicAlbumAdapter", "Showing delete dialog for item: ${item.musicTitle}")
        val fragmentManager = (context as FragmentActivity).supportFragmentManager
        val dialogFragment = MusicDeleteDialogFragment()

        dialogFragment.onDeleteConfirmed = {
            deleteMemory(item)
        }

        dialogFragment.show(fragmentManager, MusicDeleteDialogFragment.TAG)
    }

    private fun deleteMemory(item: MusicAlbumData) {
        val position = items.indexOf(item)
        if (position != -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
            Log.d("MusicAlbumAdapter", "Item deleted: ${item.musicTitle}")
        }
    }
}
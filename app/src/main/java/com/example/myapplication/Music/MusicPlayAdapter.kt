package com.example.myapplication.Music

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Diary.DiaryDayAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.AlbumMusicItemBinding
import java.util.Collections

class MusicPlayAdapter(
    private val items: ArrayList<MusicAlbumData>,
    private val dragRecyclerview: RecyclerView,
    private val itemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<MusicPlayAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var itemDragHelper: ItemTouchHelper? = null

    private val helperCallback = DragHelperCallback { fromPos, toPos ->
        Collections.swap(items, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
        Log.d("시작: $fromPos", "변경: $toPos")
        Log.d("items", "$items")
        true
    }

    init {
        dragRecyclerview.adapter = this
        itemDragHelper = ItemTouchHelper(helperCallback).apply {
            attachToRecyclerView(dragRecyclerview)
        }
    }

    inner class ViewHolder(val binding: AlbumMusicItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MusicAlbumData) {
            binding.ivItemCover.setImageResource(item.musicImgId)
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

    inner class DragHelperCallback(
        private val actionDrag: (Int, Int) -> Boolean
    ) : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            return makeMovementFlags(dragFlag, 0)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return actionDrag.invoke(viewHolder.adapterPosition, target.adapterPosition)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

        // 꾹 누르면 드래그가 시작되도록 설정
        override fun isLongPressDragEnabled(): Boolean = true
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
        }
    }
}
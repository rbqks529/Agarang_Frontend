package com.example.myapplication.Music

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
import com.example.myapplication.databinding.AlbumMusicItemBinding
import java.util.Collections

class MusicPlayAdapter(
    private val items: ArrayList<MusicAlbumData>,
    private val dragRecyclerview: RecyclerView,
    private val itemClickListener: OnItemClickListener
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
                binding.ivItemHeartEmpty.visibility = View.GONE
                binding.ivItemHeartSelected.visibility = View.VISIBLE
            }
            binding.ivItemHeartSelected.setOnClickListener {
                binding.ivItemHeartSelected.visibility = View.GONE
                binding.ivItemHeartEmpty.visibility = View.VISIBLE
            }
            itemView.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
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
}
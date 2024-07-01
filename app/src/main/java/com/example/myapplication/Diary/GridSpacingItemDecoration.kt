package com.example.myapplication.Diary

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacingHorizontal: Int,
    private val spacingVertical: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left = column * spacingHorizontal / spanCount
        outRect.right = spacingHorizontal - (column + 1) * spacingHorizontal / spanCount

        if (position >= spanCount) {
            outRect.top = spacingVertical
        }
    }
}

class SquareItemDecoration(private val spanCount: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val screenWidth = parent.width
        val itemSize = screenWidth / spanCount

        val lp = view.layoutParams
        lp.width = itemSize
        lp.height = itemSize
        view.layoutParams = lp
    }
}
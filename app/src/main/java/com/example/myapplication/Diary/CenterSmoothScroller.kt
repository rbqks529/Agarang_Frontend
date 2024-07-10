package com.example.myapplication.Diary

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

public class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
    override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
        return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
    }
}
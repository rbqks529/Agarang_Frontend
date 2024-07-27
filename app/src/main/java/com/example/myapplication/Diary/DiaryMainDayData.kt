package com.example.myapplication.Diary

import android.util.Log
import java.io.Serializable


data class DiaryMainDayData(
    val imageUrl: String,
    val date: String,
    var bookmark: Int = 0,
    val content: String = "오늘 엄마랑 대관령에 다녀왔단다.오똑숲에서 많은 나무와 아름다운 할미 꽃을 보며 정말 행복했어. 자연 속에서 느낀 피톤치드 덕분에 마음이 한결 편안해졌단다. 우리 아가도 나중에 꼭 같이 가보자. 사랑해. 우리 아가!"
) : Serializable {
    var year: Int = 0
    var month: Int = 0
    var day: Int = 1

    init {
        val parts = date.split("-").map { it.trim() }
        if (parts.size >= 2) {
            year = parts[0].toIntOrNull() ?: 0
            month = parts[1].toIntOrNull() ?: 0
        } else {
            Log.e("DiaryMainDayData", "Invalid date format: $date")
        }
    }
}
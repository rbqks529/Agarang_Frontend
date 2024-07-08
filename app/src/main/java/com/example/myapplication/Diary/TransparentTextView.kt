package com.example.myapplication.Diary

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TransparentTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12f * resources.displayMetrics.density // 12sp를 px로 변환
        textAlign = Paint.Align.CENTER
    }

    private val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = (24 * resources.displayMetrics.density).toInt() // 24dp를 px로 변환
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)

        // 원형 배경 그리기
        canvas.drawCircle(width / 2f, height / 2f, width / 2f, backgroundPaint)

        // 텍스트 경로 생성
        val textPath = Path()
        textPaint.getTextPath(text.toString(), 0, text.length, width / 2f,
            height / 2f - (textPaint.descent() + textPaint.ascent()) / 2, textPath)

        // 텍스트 경로를 따라 투명하게 그리기
        canvas.drawPath(textPath, clearPaint)

        canvas.restoreToCount(layerId)
    }
}
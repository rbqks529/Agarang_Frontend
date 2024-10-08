package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.databinding.FragmentOnboardingSecondBinding
import com.example.myapplication.databinding.FragmentOnboardingThirdBinding


class OnboardingThirdFragment : Fragment() {

    lateinit var binding: FragmentOnboardingThirdBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingThirdBinding.inflate(inflater, container, false)
        // TextView를 찾아옵니다.
        val textView: TextView = binding.tvOnboardingMent
        // 전체 텍스트 설정
        val fullText = "일상사진을 바탕으로"

        // SpannableString 생성
        val spannableString = SpannableString(fullText)

        // "일상사진" 텍스트의 시작과 끝 인덱스를 찾아 색상 적용
        val startIndex = fullText.indexOf("일상사진")
        val endIndex = startIndex + "일상사진".length
        val color = Color.parseColor("#EB5F2A")

        // ForegroundColorSpan을 사용하여 색상 적용
        spannableString.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // TextView에 SpannableString 설정
        textView.text = spannableString

        return binding.root
    }


}
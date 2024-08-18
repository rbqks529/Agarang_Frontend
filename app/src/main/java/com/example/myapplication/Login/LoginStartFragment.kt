package com.example.myapplication.Login

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.Retrofit.LoginIF
import com.example.myapplication.Retrofit.RetrofitService
import com.example.myapplication.databinding.FragmentLoginStartBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginStartFragment : Fragment() {

    private lateinit var binding: FragmentLoginStartBinding
    private lateinit var cookieManager: CookieManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginStartBinding.inflate(inflater, container, false)

        binding.icLoginKakao.setOnClickListener {
            authorizeKakao()
        }

        binding.icLoginGoogle.setOnClickListener {
            authorizeGoogle()
        }

        // WebView 설정
        binding.webView.settings.javaScriptEnabled = true
        cookieManager = CookieManager.getInstance() // CookieManager 인스턴스 가져오기
        cookieManager.setAcceptCookie(true)

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.d("WebView", "Loading URL: $url")
                url?.let {
                    if (it.equals("https://www.agarang.site/")) {
                        // 로그인 성공 후 리다이렉트된 URL 감지
                        handleLoginSuccess(it)
                        return true
                    }
                }
                val cookies = cookieManager.getCookie(url)
                Log.d("쿠키 확인", "Received cookies: $cookies")
                return false
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                Log.e("WebView", "Error: ${error?.description}, Code: ${error?.errorCode}")
                super.onReceivedError(view, request, error)
            }
        }

        return binding.root
    }

    private fun authorizeGoogle() {
        binding.webView.getSettings().setUserAgentString("Mozilla/5.0 AppleWebKit/535.19 Chrome/56.0.0 Mobile Safari/535.19");

        activity?.runOnUiThread {
            binding.webView.visibility = View.VISIBLE
            binding.webView.loadUrl("https://www.agarang.site/oauth2/authorization/google")
        }
    }

    private fun authorizeKakao() {

        binding.webView.visibility = View.VISIBLE
        binding.webView.loadUrl("https://www.agarang.site/oauth2/authorization/kakao")
       /* val loginService = RetrofitService.createRetrofit(requireContext()).create(LoginIF::class.java)

        loginService.authorizeKakao().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val loginUrl = response.raw().request.url.toString()
                    activity?.runOnUiThread {
                        binding.webView.visibility = View.VISIBLE
                        binding.webView.loadUrl(loginUrl)
                    }
                    *//*val cookies = response.headers()["Set-Cookie"]

                    val authToken = extractAuthToken(cookies)
                    if (authToken != null) {
                        saveAuthToken(authToken)
                        Log.d("토큰 확인", "Received JWT: $authToken")
                    } else {
                        Log.e("토큰 에러", "Authorization token not found in cookies")
                    }*//*
                } else {
                    Toast.makeText(context, "에러: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })*/
    }

    private fun handleLoginSuccess(url: String) {
        // WebView 숨기기
        binding.webView.visibility = View.GONE

        val cookies = cookieManager.getCookie(url)
        val authToken = extractAuthToken(cookies)
        if (authToken != null) {
            saveAuthToken(authToken)
            Log.d("토큰 확인", "Received JWT: $authToken")

            // 쿠키를 CookieJar에 추가
            RetrofitService.addCookie(url, "ACCESS=$authToken")
        } else {
            Log.e("토큰 에러", "Authorization token not found in cookies: $cookies")
        }

        Toast.makeText(context, "로그인 성공!", Toast.LENGTH_SHORT).show()


        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_frm2, LoginCode1Fragment())
            ?.commit()
    }

    private fun extractAuthToken(cookies: String?): String? {
        return cookies?.split(";")?.find { it.trim().startsWith("ACCESS=") }?.substringAfter("=")
    }

    private fun saveAuthToken(token: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("auth_token", token).apply()

        Log.d("토큰 저장", "Auth token saved: $token")
    }
}
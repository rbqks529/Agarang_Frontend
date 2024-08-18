package com.example.myapplication.Retrofit

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

class PersistentCookieJar : CookieJar {
    private val cookieStore = HashMap<String, List<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url.host] = cookies
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore[url.host] ?: ArrayList()
    }

    fun addCookie(url: String, cookieString: String) {
        val httpUrl = url.toHttpUrlOrNull() ?: return
        val cookie = Cookie.parse(httpUrl, cookieString) ?: return
        val cookies = (cookieStore[httpUrl.host] ?: ArrayList()).toMutableList()
        cookies.add(cookie)
        cookieStore[httpUrl.host] = cookies
    }
}
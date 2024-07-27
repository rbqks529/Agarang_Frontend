package com.example.myapplication.Retrofit

import android.content.Context
import com.example.myapplication.R
import java.io.IOException
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

class SelfSigningHelper(private val context: Context) {
    lateinit var tmf: TrustManagerFactory
    val sslContext: SSLContext by lazy { createSSLContext() }

    private fun createSSLContext(): SSLContext {
        try {
            val cf = CertificateFactory.getInstance("X.509")
            context.resources.openRawResource(R.raw.agarang).use { caInput ->
                val ca = cf.generateCertificate(caInput)
                println("ca = ${(ca as X509Certificate).subjectDN}")

                val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
                    load(null, null)
                    setCertificateEntry("ca", ca)
                }

                val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
                val tmf = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
                    init(keyStore)
                }

                return SSLContext.getInstance("TLS").apply {
                    init(null, tmf.trustManagers, java.security.SecureRandom())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("SSL 컨텍스트 초기화 실패", e)
        }
    }
}
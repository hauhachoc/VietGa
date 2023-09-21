package org.nekomanga.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        return if (originalRequest.header("User-Agent").isNullOrEmpty()) {
            val newRequest = originalRequest
                .newBuilder()
                .removeHeader("User-Agent")
                .addHeader("User-Agent", USER_AGENT_SHORT)
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }

    companion object {
        const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";
        const val USER_AGENT_SHORT =  "Mozilla/5.0 (Windows NT 6.3; WOW64)"
    }
}

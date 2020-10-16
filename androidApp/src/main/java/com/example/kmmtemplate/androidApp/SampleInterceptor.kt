package com.example.kmmtemplate.androidApp

import okhttp3.Interceptor
import okhttp3.Response

object SampleInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        println("SampleInterceptor")
        return chain.proceed(chain.request())
    }
}

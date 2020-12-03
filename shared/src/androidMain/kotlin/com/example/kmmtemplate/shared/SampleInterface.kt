package com.example.kmmtemplate.shared

import retrofit2.http.GET

actual interface SampleInterface {
    @GET("")
    actual fun test()
}

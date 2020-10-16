package com.example.kmmtemplate.androidApp

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class KotlinxGsonFactory(
    private val kotlinx: Converter.Factory,
    private val gson: Converter.Factory
) : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? =
        runCatching { kotlinx.responseBodyConverter(type, annotations, retrofit) }
            .onFailure { gson.responseBodyConverter(type, annotations, retrofit) }
            .getOrThrow()

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? =
        runCatching {
            kotlinx.requestBodyConverter(
                type,
                parameterAnnotations,
                methodAnnotations,
                retrofit
            )
        }
            .onFailure {
                gson.requestBodyConverter(
                    type,
                    parameterAnnotations,
                    methodAnnotations,
                    retrofit
                )
            }
            .getOrThrow()

    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? =
        runCatching { kotlinx.stringConverter(type, annotations, retrofit) }
            .onFailure { gson.stringConverter(type, annotations, retrofit) }
            .getOrNull()
}

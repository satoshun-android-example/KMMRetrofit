package com.example.kmmtemplate.androidApp

import kotlinx.serialization.Serializable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class KotlinxGsonFactory(
  private val kotlinx: Converter.Factory,
  private val gson: Converter.Factory
) : Converter.Factory() {
  override fun responseBodyConverter(
    type: Type,
    annotations: Array<Annotation>,
    retrofit: Retrofit
  ): Converter<ResponseBody, *>? {
    return if (isKotlinx(type)) kotlinx.responseBodyConverter(type, annotations, retrofit)
    else gson.responseBodyConverter(type, annotations, retrofit)
  }

  private fun isKotlinx(type: Type): Boolean {
    if (type !is ParameterizedType) return false
    val annotations = (type.actualTypeArguments.first() as? Class<*>)?.annotations ?: return false
    return annotations.any { it is Serializable }
  }

  override fun requestBodyConverter(
    type: Type,
    parameterAnnotations: Array<Annotation>,
    methodAnnotations: Array<Annotation>,
    retrofit: Retrofit
  ): Converter<*, RequestBody>? {
    TODO()
  }

  override fun stringConverter(
    type: Type,
    annotations: Array<Annotation>,
    retrofit: Retrofit
  ): Converter<*, String>? {
    val isKotlinX = annotations.any { it is Serializable }
    return if (isKotlinX) kotlinx.stringConverter(type, annotations, retrofit)
    else gson.stringConverter(type, annotations, retrofit)
  }
}

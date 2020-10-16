package com.example.kmmtemplate.androidApp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kmmtemplate.shared.GithubApi
import com.example.kmmtemplate.shared.Greeting
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun greet(): String {
  return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
  @OptIn(ExperimentalSerializationApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val tv: TextView = findViewById(R.id.text_view)
    tv.text = greet()

    val okHttpClient = OkHttpClient.Builder()
      .addInterceptor(SampleInterceptor)
      .build()

    val json = Json {
      ignoreUnknownKeys = true
    }
    val gitHubApi = GithubApi(
      httpClient = HttpClient(OkHttp) {
        engine {
          preconfigured = okHttpClient
//          addInterceptor(SampleInterceptor)
        }

        install(JsonFeature) {
          serializer = KotlinxSerializer(
            json = json
          )
        }
      }
    )

    lifecycleScope.launchWhenStarted {
      val repos = gitHubApi.getRepos("satoshun")
      println(repos)
    }

    val contentType = "application/json".toMediaType()
    val retrofitCreator = Retrofit.Builder()
      .baseUrl("https://api.github.com")
      .addConverterFactory(json.asConverterFactory(contentType))
      .addConverterFactory(GsonConverterFactory.create())
//      .addConverterFactory(KotlinxGsonFactory(
//        kotlinx = json.asConverterFactory(contentType),
//        gson = GsonConverterFactory.create()
//      ))
      .client(okHttpClient)
      .build()

    val kotlinxGithubApi = retrofitCreator.create(RetrofitKotlinxGitHubApi::class.java)

    lifecycleScope.launchWhenStarted {
      val repos = kotlinxGithubApi.getRepos("satoshun")
      println("kotlinx $repos")
    }

    val gsonGithubApi = retrofitCreator.create(RetrofitGsonGitHubApi::class.java)
    lifecycleScope.launchWhenStarted {
      val repos = gsonGithubApi.getRepos("satoshun")
      println("gsonGithubApi $repos")
    }
  }
}

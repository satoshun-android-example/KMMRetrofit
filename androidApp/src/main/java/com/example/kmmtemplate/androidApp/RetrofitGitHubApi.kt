package com.example.kmmtemplate.androidApp

import com.example.kmmtemplate.shared.GithubRepo
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path

// use kotlinx
interface RetrofitKotlinxGitHubApi {
  @GET("users/{user}/repos")
  suspend fun getRepos(@Path("user") user: String): List<GithubRepo>
}

// use gson
interface RetrofitGsonGitHubApi {
  @GET("users/{user}/repos")
  suspend fun getRepos(@Path("user") user: String): List<GsonGithubRepo>

  @GET("users/{user}/repos")
  suspend fun getRepo(@Path("user") user: String): GsonGithubRepo
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestAnnotation2

@TestAnnotation2
data class GsonGithubRepo(
  @SerializedName("name") val repoName: String
)

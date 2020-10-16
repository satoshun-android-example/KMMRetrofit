package com.example.kmmtemplate.androidApp

import com.example.kmmtemplate.shared.GithubRepo
import com.example.kmmtemplate.shared.GithubUser
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// use kotlinx
interface RetrofitKotlinxGitHubApi {
  @GET("users/{user}/repos")
  suspend fun getRepos(@Path("user") user: String): List<GithubRepo>

  @GET("users/{user}/repos")
  suspend fun getReposResponse(@Path("user") user: String): Response<List<GithubRepo>>

  @GET("users/{userName}")
  suspend fun getUser(@Path("userName") userName: String): GithubUser

  @GET("users/{userName}")
  suspend fun getUserResponse(@Path("userName") userName: String): Response<GithubUser>
}

// use gson
interface RetrofitGsonGitHubApi {
  @GET("users/{user}/repos")
  suspend fun getRepos(@Path("user") user: String): List<GsonGithubRepo>
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestAnnotation2

@TestAnnotation2
data class GsonGithubRepo(
  @SerializedName("name") val repoName: String
)

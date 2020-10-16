package com.example.kmmtemplate.shared

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRepo(
  @SerialName("name") val repoName: String
)

@Serializable
data class GithubUser(
  @SerialName("name") val userName: String
)

class GithubApi(
  private val httpClient: HttpClient
) {
  private val apiEndpoint = "https://api.github.com"

  suspend fun getRepos(user: String): NetworkResult<List<GithubRepo>> =
    get("$apiEndpoint/users/$user/repos")

  private suspend inline fun <reified T> get(url: String): NetworkResult<T> =
    runCatching {
      httpClient.get<T> {
        url(url)
        accept(ContentType.Application.Json)
      }
    }.fold(
      onSuccess = { NetworkResult.Success(it) },
      onFailure = { NetworkResult.Error(it) },
    )
}

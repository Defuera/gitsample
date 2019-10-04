package ru.justd.githubrepos.app.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

private const val BASE_URL = "https://api.github.com"

private const val ENDPOINT_USER_REPOSITORIES = "/users/{username}/repos"

class GithubApi(private val okHttp: OkHttpClient) {

    fun getUserRepositories(username: String): Response {
        return okHttp
            .newCall(
                Request.Builder()
                    .url("$BASE_URL${ENDPOINT_USER_REPOSITORIES.withPathParam(username)}")
                    .build()
            )
            .execute()

    }

}

private fun String.withPathParam(param: String): String = replace(Regex("\\{.+}"), param)


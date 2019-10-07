package ru.justd.githubrepos.app.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

private const val BASE_URL = "https://api.github.com"

private const val ENDPOINT_USER_REPOSITORIES = "/users/{username}/repos"
private const val ENDPOINT_COMMITS = "/repos/{username}/{repo}/commits"

class GithubApi(private val okHttp: OkHttpClient) {

    fun getUserRepositories(username: String): Response =
        okHttp.newCall(
            Request.Builder()
                .url("$BASE_URL${ENDPOINT_USER_REPOSITORIES.withPathParam(username)}")
                .build()
        )
            .execute()

    fun getCommits(username: String, repositoryId: String): Response =
        okHttp.newCall(
            Request.Builder()
                .url("$BASE_URL${getEndpointCommits(username, repositoryId)}")
                .build()
        )
            .execute()


    private fun getEndpointCommits(username: String, repositoryId: String) =
        ENDPOINT_COMMITS
            .replace("{username}", username)
            .replace("{repo}", repositoryId)


}

private fun String.withPathParam(param: String): String = replace(Regex("\\{.+\\}"), param)


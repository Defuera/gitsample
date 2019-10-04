package ru.justd.githubrepos.repositories.data

import androidx.annotation.StringRes
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ru.justd.githubrepos.R
import ru.justd.githubrepos.app.network.GithubApi


class RepositoriesInteractor(
    private val api: GithubApi
) {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun getRepositories(query: String): Result {
        return try {
            val response = api.getUserRepositories(query)
            if (response.isSuccessful) {
                val jsonString = response.body?.string()
                    ?: return Result.Error(R.string.generic_error)

                val listMyData =
                    Types.newParameterizedType(List::class.java, Repository::class.java)
                val adapter = moshi.adapter<List<Repository>>(listMyData)
                val repos = adapter.fromJson(jsonString)
                if (repos.isNullOrEmpty()) {
                    Result.Error(R.string.generic_error)
                } else {
                    Result.Success(repos)
                }
            } else {
                if (response.code == 404) {
                    Result.Error(R.string.no_data)
                } else {
                    Result.Error(R.string.generic_error)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(R.string.network_error)
        }

    }
}

sealed class Result {
    data class Error(@StringRes val errorMessage: Int) : Result()
    data class Success(val repository: List<Repository>) : Result()

}
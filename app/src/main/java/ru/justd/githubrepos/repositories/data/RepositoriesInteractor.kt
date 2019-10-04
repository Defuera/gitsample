package ru.justd.githubrepos.repositories.data

import androidx.annotation.StringRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.justd.githubrepos.R
import ru.justd.githubrepos.app.network.GithubApi

class RepositoriesInteractor(
    private val api: GithubApi
) {
    fun getRepositories(query: String): Result {
        return try {
            val response = api.getUserRepositories(query)
            if (response.isSuccessful) {
                val typeToken = object : TypeToken<List<Repository>>() {}.type
                val repos = Gson().fromJson<List<Repository>>(response.body.toString(), typeToken)
                Result.Success(repos)
            } else {
                //get server error
                Result.Error(R.string.generic_error)
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
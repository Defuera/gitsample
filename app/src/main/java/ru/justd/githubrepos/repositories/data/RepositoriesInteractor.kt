package ru.justd.githubrepos.repositories.data

import androidx.annotation.StringRes
import ru.justd.githubrepos.R
import java.io.IOException


class RepositoriesInteractor(
    private val dataSource: RepositoryDataSource
) {

    fun getRepositories(query: String): Result = try {
        val repositories = dataSource.getRepositories(query)
        if (repositories.isNullOrEmpty()) {
            Result.Error(R.string.no_data)
        } else {
            Result.Success(repositories)
        }

    } catch (e: IOException) {
        Result.Error(R.string.network_error)
    } catch (e: UnexpectedResponse) {
        Result.Error(R.string.generic_error)
    }

}

sealed class Result {
    data class Error(@StringRes val errorMessage: Int) : Result()
    data class Success(val repository: List<Repository>) : Result()

}

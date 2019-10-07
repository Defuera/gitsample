package ru.justd.githubrepos.commits.data

import androidx.annotation.StringRes
import ru.justd.githubrepos.R
import java.io.IOException


class CommitsInteractor(
    private val dataSource: CommitDataSource
) {

    fun getCommits(username: String, repositoryName: String): Result = try {
        val commits = dataSource.getCommits(username, repositoryName)
        if (commits.isNullOrEmpty()) {
            Result.Error(R.string.no_data)
        } else {
            Result.Success(commits)
        }

    } catch (e: IOException) {
        Result.Error(R.string.network_error)
    } catch (e: UnexpectedFailure) {
        Result.Error(R.string.generic_error)
    }

}

sealed class Result {
    data class Error(@StringRes val errorMessage: Int) : Result()
    data class Success(val Commit: List<Commit>) : Result()

}

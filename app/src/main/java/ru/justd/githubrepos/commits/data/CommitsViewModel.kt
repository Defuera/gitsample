package ru.justd.githubrepos.commits.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.justd.githubrepos.extensions.exhaustive

class CommitsViewModel(
    private val user: String,
    private val repositoryName: String,
    private val interactor: CommitsInteractor,
    val stateHolder: CommitsStateHolder
) : ViewModel() {


    init {
        fetchCommits()
    }

    private fun fetchCommits() {
        viewModelScope.launch {
            stateHolder.setLoadingState()
            val result = withContext(Dispatchers.IO) { interactor.getCommits(user, repositoryName) }
            when (result) {
                is Result.Error -> stateHolder.setErrorState(result.errorMessage)
                is Result.Success -> stateHolder.setDataState(result.Commit)
            }.exhaustive
        }
    }

}
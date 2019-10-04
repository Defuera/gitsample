package ru.justd.githubrepos.repositories.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.justd.githubrepos.app.Router
import ru.justd.githubrepos.extentions.exhaustive

sealed class Event {
    class RepositoryClicked(val item: Repository) : Event()
    class SearchInputUpdate(val username: String) : Event()
}

private const val DEBOUNCE_TIME_MS = 600L

class RepositoriesViewModel(
    private val interactor: RepositoriesInteractor,
    private val router: Router,
    val stateHolder: RepositoriesStateHolder
) : ViewModel() {

    private var query = ""

    init {
        stateHolder.setInitialState()
    }


    //region handle events

    fun dispatch(event: Event) {
        when (event) {
            is Event.RepositoryClicked -> onRepositoryClicked(event.item)
            is Event.SearchInputUpdate -> onSearchInputFieldUpdated(event.username)
        }
    }

    private fun onRepositoryClicked(item: Repository) {
        router.navigateToRepositoryPage(item.id)
    }

    private fun onSearchInputFieldUpdated(input: String) {
        if (query != input) {
            query = input
            GlobalScope.launch {
                delay(DEBOUNCE_TIME_MS)
                if (query == input) {
                    fetchRepositories(query)
                }
            }
        }
    }

    //endregion


    private fun fetchRepositories(query: String) {
        viewModelScope.launch {
            stateHolder.setLoadingState()
            val result = withContext(Dispatchers.IO) { interactor.getRepositories(query) }
            when (result) {
                is Result.Error -> stateHolder.setErrorState(result.errorMessage)
                is Result.Success -> stateHolder.setDataState(result.repository)
            }.exhaustive
        }
    }

}
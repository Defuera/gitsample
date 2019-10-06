package ru.justd.githubrepos.repositories.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.justd.githubrepos.app.BaseViewModel
import ru.justd.githubrepos.app.Router
import ru.justd.githubrepos.extensions.exhaustive

sealed class RepositoriesEvent {
    class RepositoryClicked(val item: Repository) : RepositoriesEvent()
    class SearchInputUpdate(val username: String) : RepositoriesEvent()
}

private const val DEBOUNCE_TIME_MS = 600L

class RepositoriesViewModel(
    private val interactor: RepositoriesInteractor,
    private val router: Router,
    val stateHolder: RepositoriesStateHolder,
    val debounceTime: Long = DEBOUNCE_TIME_MS
) : BaseViewModel<RepositoriesEvent, RepositoriesState>() {

    private var query = ""

    init {
        stateHolder.setInitialState()
    }


    //region BaseViewModel

    override fun dispatch(event: RepositoriesEvent) {
        when (event) {
            is RepositoriesEvent.RepositoryClicked -> onRepositoryClicked(event.item)
            is RepositoriesEvent.SearchInputUpdate -> onSearchInputFieldUpdated(event.username)
        }
    }

    override fun observeViewStates(owner: LifecycleOwner, observer: Observer<RepositoriesState>) {
        stateHolder.observe(owner, observer)
    }

    //endregion


    //region handle events

    private fun onRepositoryClicked(item: Repository) {
        router.navigateToRepositoryPage(item.id)
    }

    private fun onSearchInputFieldUpdated(input: String) {
        if (query != input) {
            query = input
            GlobalScope.launch {
                delay(debounceTime)
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
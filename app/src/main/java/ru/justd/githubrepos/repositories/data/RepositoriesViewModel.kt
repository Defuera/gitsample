package ru.justd.githubrepos.repositories.data

import androidx.lifecycle.ViewModel
import ru.justd.githubrepos.app.Router

sealed class Event {
    class RepositoryClicked(val item: Repository) : Event()
    class SearchInputUpdate(val username: String) : Event()
}

class RepositoriesViewModel(
    private val interactor: RepositoriesInteractor,
    private val router: Router,
    val stateHolder: RepositoriesStateHolder
) : ViewModel() {


    init {
        stateHolder.setInitialState()
    }

    fun dispatch(event: Event) {
        when (event) {
            is Event.RepositoryClicked -> onRepositoryClicked(event.item)
            is Event.SearchInputUpdate -> onSearchInputFieldUpdated(event.username)
        }
    }

    private fun onSearchInputFieldUpdated(username: String) {

    }

    private fun onRepositoryClicked(item: Repository) {
        router.navigateToRepositoryPage(item.id)
    }


}
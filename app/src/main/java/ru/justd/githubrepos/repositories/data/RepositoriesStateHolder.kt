package ru.justd.githubrepos.repositories.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.justd.githubrepos.extentions.reObserve

class RepositoriesStateHolder {

    private val stateHolderLiveData = MutableLiveData<RepositoriesState>()

    fun setInitialState() = stateHolderLiveData.postValue(RepositoriesState.Initial)

    fun setLoadingState() = stateHolderLiveData.postValue(RepositoriesState.Loading)

    fun setDataState(repositories: List<Repository>) =
        stateHolderLiveData.postValue(RepositoriesState.Data(repositories))

    fun setErrorState(errorMessage: String) =
        stateHolderLiveData.postValue(RepositoriesState.Error(errorMessage))

    fun observe(owner: LifecycleOwner, observer: Observer<RepositoriesState>) {
        stateHolderLiveData.reObserve(owner, observer)
    }


}

sealed class RepositoriesState {
    object Initial : RepositoriesState()
    object Loading : RepositoriesState()
    data class Data(val repositories: List<Repository>) : RepositoriesState()
    data class Error(val errorMessage: String) : RepositoriesState()
}
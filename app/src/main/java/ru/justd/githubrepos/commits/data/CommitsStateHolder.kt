package ru.justd.githubrepos.commits.data

import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.justd.githubrepos.extensions.reObserve

class CommitsStateHolder {

    private val stateHolderLiveData = MutableLiveData<CommitsState>()

    fun setLoadingState() = stateHolderLiveData.postValue(CommitsState.Loading)

    fun setDataState(Commits: List<Commit>) =
        stateHolderLiveData.postValue(CommitsState.Data(Commits))

    fun setErrorState(@StringRes errorMessage: Int) =
        stateHolderLiveData.postValue(CommitsState.Error(errorMessage))

    fun observe(owner: LifecycleOwner, observer: Observer<CommitsState>) {
        stateHolderLiveData.reObserve(owner, observer)
    }


}

sealed class CommitsState {
    object Loading : CommitsState()
    data class Data(val Commits: List<Commit>) : CommitsState()
    data class Error(@StringRes val errorMessage: Int) : CommitsState()
}
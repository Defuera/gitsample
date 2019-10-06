package ru.justd.githubrepos.app

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<E, S> : ViewModel() {

    abstract fun dispatch(event: E)
    abstract fun observeViewStates(owner: LifecycleOwner, observer: Observer<S>)

}
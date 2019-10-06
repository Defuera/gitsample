package ru.justd.githubrepos.repositories.utils

import android.app.Activity
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import ru.justd.githubrepos.app.BaseViewModel

fun <E, S> BaseViewModel<E, S>.setState(activity: Activity, state: S) {
    val viewModel = this
    activity.runOnUiThread {
        argumentCaptor<Observer<S>> {
            verify(viewModel, times(1)).observeViewStates(any(), capture())
            firstValue.onChanged(state)
        }
    }
}
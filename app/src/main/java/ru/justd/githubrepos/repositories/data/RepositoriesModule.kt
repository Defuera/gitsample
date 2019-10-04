package ru.justd.githubrepos.repositories.data

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object RepositoriesModule {

    val module = module {

        factory { RepositoriesInteractor(api = get()) }

        viewModel { RepositoriesViewModel(interactor = get(), router = get(), stateHolder = RepositoriesStateHolder()) }
    }

}
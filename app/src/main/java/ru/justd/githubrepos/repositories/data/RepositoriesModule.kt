package ru.justd.githubrepos.repositories.data

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object RepositoriesModule {

    val module = module {

        factory { RepositoryDataSource(api = get()) }

        factory { RepositoriesInteractor(dataSource = get()) }

        viewModel { RepositoriesViewModel(interactor = get(), router = get(), stateHolder = RepositoriesStateHolder()) }
    }

}
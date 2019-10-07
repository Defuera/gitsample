package ru.justd.githubrepos.commits.data

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object CommitsModule {

    val module = module {

        factory { CommitDataSource(api = get()) }

        factory { CommitsInteractor(dataSource = get()) }

        viewModel { (user: String, repositoryName: String) ->
            CommitsViewModel(
                user = user,
                repositoryName = repositoryName,
                interactor = get(),
                stateHolder = CommitsStateHolder()
            )
        }
    }

}
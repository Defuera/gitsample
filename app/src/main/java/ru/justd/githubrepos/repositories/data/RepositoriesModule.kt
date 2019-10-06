package ru.justd.githubrepos.repositories.data

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.justd.githubrepos.app.BaseViewModel

object RepositoriesModule {

    val module = module {

        factory { RepositoryDataSource(api = get()) }

        factory { RepositoriesInteractor(dataSource = get()) }

        viewModel<BaseViewModel<RepositoriesEvent, RepositoriesState>> {
            RepositoriesViewModel(
                interactor = get(),
                router = get(),
                stateHolder = RepositoriesStateHolder()
            )
        }
    }

}
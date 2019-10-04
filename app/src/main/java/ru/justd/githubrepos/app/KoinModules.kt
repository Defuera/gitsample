package ru.justd.githubrepos.app

import ru.justd.githubrepos.repositories.data.RepositoriesModule

val koinModules = listOf(
    AppModule.module,
    RepositoriesModule.module
)
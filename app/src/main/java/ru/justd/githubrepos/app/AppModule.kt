package ru.justd.githubrepos.app

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import ru.justd.githubrepos.app.network.GithubApi

object AppModule {

    val module = module {

        single {
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                )
                .build()

        }

        single {
            GithubApi(okHttp = get())
        }

        factory { Router() }
    }

}
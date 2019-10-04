package ru.justd.githubrepos.app

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import ru.justd.githubrepos.app.network.GithubApi
import java.util.concurrent.TimeUnit

object AppModule {

    val module = module {

        single {
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                )
                .callTimeout(2, TimeUnit.SECONDS)
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build()

        }

        single {
            GithubApi(okHttp = get())
        }

        factory { Router() }
    }

}
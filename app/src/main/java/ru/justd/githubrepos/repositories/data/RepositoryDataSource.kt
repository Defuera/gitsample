package ru.justd.githubrepos.repositories.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ru.justd.githubrepos.app.network.GithubApi

class UnexpectedResponse : RuntimeException()

class RepositoryDataSource(private val api: GithubApi) {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun getRepositories(query: String): List<Repository>? {
        val response = api.getUserRepositories(query)
        
        if (response.isSuccessful) {
            val jsonString = response.body?.string() ?: throw UnexpectedResponse()
            val listMyData = Types.newParameterizedType(List::class.java, Repository::class.java)
            val adapter = moshi.adapter<List<Repository>>(listMyData)
            return adapter.fromJson(jsonString)
        } else {
            throw UnexpectedResponse()
        }

    }

}
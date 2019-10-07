package ru.justd.githubrepos.commits.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ru.justd.githubrepos.app.network.GithubApi

class UnexpectedFailure : RuntimeException()

class CommitDataSource(private val api: GithubApi) {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun getCommits(username: String, repositoryName: String): List<Commit>? {
        val response = api.getCommits(username, repositoryName)
        
        if (response.isSuccessful) {
            val jsonString = response.body?.string() ?: throw UnexpectedFailure()
            val listMyData = Types.newParameterizedType(List::class.java, Commit::class.java)
            val adapter = moshi.adapter<List<Commit>>(listMyData)
            return adapter.fromJson(jsonString)
        } else {
            throw UnexpectedFailure()
        }

    }

}
package ru.justd.githubrepos.repositories.data

data class Repository(
    val id: String,
    val name: String,
    val private: Boolean,
    val description: String?,
    val fork: Boolean,
    val language: String?,
    val stargazers: Int?,
    val forks: Int
)

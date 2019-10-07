package ru.justd.githubrepos.commits.data

data class Commit(
    val author: Author?,
    val commit: CommitData
)

data class Author(
    val login: String,
    val avatar_url: String
)

data class CommitData(
    val message: String?
)

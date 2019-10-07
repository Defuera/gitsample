package ru.justd.githubrepos.app

import androidx.appcompat.app.AppCompatActivity
import ru.justd.githubrepos.R
import ru.justd.githubrepos.commits.CommitsFragment
import ru.justd.githubrepos.repositories.RepositoriesFragment

class Router {

    private lateinit var activity: AppCompatActivity

    fun init(activity: AppCompatActivity) {
        this.activity = activity
    }

    fun navigateToCommitsPage(username: String, repoId: String) {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CommitsFragment.newInstance(username, repoId))
            .addToBackStack("root")
            .commit()
    }

    fun showSearchRepositoriesPage() {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, RepositoriesFragment())
            .addToBackStack(null)
            .commit()
    }

}
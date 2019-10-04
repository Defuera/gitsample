package ru.justd.githubrepos.app

import androidx.appcompat.app.AppCompatActivity
import ru.justd.githubrepos.R
import ru.justd.githubrepos.repositories.RepositoriesFragment

class Router {

    private lateinit var activity: AppCompatActivity

    fun init(activity: AppCompatActivity) {
        this.activity = activity
    }

    fun navigateToRepositoryPage(id: String) {

    }

    fun showSearchPage() {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, RepositoriesFragment())
            .commit()
    }

}
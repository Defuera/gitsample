package ru.justd.githubrepos.repositories

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotterknife.bindView
import ru.justd.githubrepos.R
import ru.justd.githubrepos.repositories.data.Repository

class RepositoryWidget(context: Context) : LinearLayout(context) {

    private val privateLabel by bindView<TextView>(R.id.private_label)
    private val forkedLabel by bindView<TextView>(R.id.forked_label)
    private val nameLabel by bindView<TextView>(R.id.name_value)
    private val starsValue by bindView<TextView>(R.id.stars_value)
    private val forksValue by bindView<TextView>(R.id.forks_value)
    private val languageValue by bindView<TextView>(R.id.language_value)

    init {
        View.inflate(context, R.layout.widget_repository, this)
    }

    fun bind(repository: Repository) {
        privateLabel.setVisibleOrGone(repository.private)
        forkedLabel.setVisibleOrGone(repository.fork)
        nameLabel.text = repository.name
        starsValue.text = repository.stargazers.toString()
        forksValue.text = repository.forks.toString()
        languageValue.text = repository.language
    }


}

private fun TextView.setVisibleOrGone(shouldBeVisible: Boolean) {
    visibility = if (shouldBeVisible) View.VISIBLE else View.GONE
}

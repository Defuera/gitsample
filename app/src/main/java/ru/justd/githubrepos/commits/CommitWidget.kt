package ru.justd.githubrepos.commits

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotterknife.bindView
import ru.justd.githubrepos.R
import ru.justd.githubrepos.commits.data.Commit

class CommitWidget(context: Context) : LinearLayout(context) {

    private val avatar by bindView<ImageView>(R.id.avatar)
    private val commitMessage by bindView<TextView>(R.id.commit_message_value)
    private val username by bindView<TextView>(R.id.username)

    init {
        View.inflate(context, R.layout.widget_commit, this)
    }

    fun bind(commit: Commit) {
        commitMessage.text = commit.commit.message
        username.text = commit.author?.login
        Picasso.get()
            .load(commit.author?.avatar_url)
            .into(avatar)
    }

}
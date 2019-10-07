package ru.justd.githubrepos.commits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.RecyclerView.LayoutParams.WRAP_CONTENT
import kotterknife.bindView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.justd.duperadapter.ArrayListDuperAdapter
import ru.justd.githubrepos.R
import ru.justd.githubrepos.app.widgets.ErrorWidget
import ru.justd.githubrepos.commits.data.Commit
import ru.justd.githubrepos.commits.data.CommitsState
import ru.justd.githubrepos.commits.data.CommitsViewModel
import ru.justd.githubrepos.extensions.exhaustive
import ru.justd.lilwidgets.LilLoaderWidget

class CommitsFragment : Fragment() {

    companion object {
        const val EXTRA_USERNAME = "EXTRA_USERNAME"
        const val EXTRA_REPOSITORY_ID = "EXTRA_REPOSITORY_ID"

        fun newInstance(username: String, repositoryName: String) =
            CommitsFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USERNAME, username)
                    putString(EXTRA_REPOSITORY_ID, repositoryName)
                }
            }
    }

    private val viewModel by viewModel<CommitsViewModel> {
        parametersOf(
            getUsernameArg(),
            getRepositoryNameArg()
        )
    }

    private val recycler by bindView<RecyclerView>(R.id.commits_recycler_view)
    private val lilWidget by bindView<LilLoaderWidget>(R.id.commits_lil_widget)

    private val adapter = ArrayListDuperAdapter().apply {
        addViewType<Commit, CommitWidget>(Commit::class.java)
            .addViewCreator { parent ->
                CommitWidget(parent.context).apply {
                    layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                }
            }
            .addViewBinder { widget, item -> widget.bind(item) }
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_commits, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.adapter = adapter

        viewModel.stateHolder.observe(this,
            Observer {
                when (it) {
                    is CommitsState.Loading -> showLoadingState()
                    is CommitsState.Data -> showData(it.Commits)
                    is CommitsState.Error -> showError(it.errorMessage)
                }.exhaustive
            })
    }

    //region ui states

    private fun showLoadingState() {
        lilWidget.showLoading()
        recycler.visibility = View.INVISIBLE
    }

    private fun showError(@StringRes errorMessage: Int) {
        recycler.visibility = View.INVISIBLE
        lilWidget.showError {
            ErrorWidget(it).apply { errorText = errorMessage }
        }
    }

    private fun showData(commits: List<Commit>) {
        recycler.visibility = View.VISIBLE
        lilWidget.hide()
        adapter.addAll(commits)
        adapter.notifyDataSetChanged()
    }

    //endregion


    //region helper methods

    private fun getUsernameArg() =
        requireNotNull(arguments?.getString(EXTRA_USERNAME)) { IllegalArgumentException("Username argument must be provided") }

    private fun getRepositoryNameArg() =
        requireNotNull(arguments?.getString(EXTRA_REPOSITORY_ID)) { IllegalArgumentException("Repository id argument must be provided") }

    //endregion
}
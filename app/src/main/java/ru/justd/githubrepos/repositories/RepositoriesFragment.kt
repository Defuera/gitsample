package ru.justd.githubrepos.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.RecyclerView.LayoutParams.WRAP_CONTENT
import kotterknife.bindView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.justd.duperadapter.ArrayListDuperAdapter
import ru.justd.githubrepos.R
import ru.justd.githubrepos.app.BaseViewModel
import ru.justd.githubrepos.app.widgets.ErrorWidget
import ru.justd.githubrepos.app.extensions.exhaustive
import ru.justd.githubrepos.repositories.data.RepositoriesEvent
import ru.justd.githubrepos.repositories.data.RepositoriesState
import ru.justd.githubrepos.repositories.data.Repository
import ru.justd.lilwidgets.LilLoaderWidget

class RepositoriesFragment : Fragment() {

    private val viewModel by viewModel<BaseViewModel<RepositoriesEvent, RepositoriesState>>()

    private val searchView by bindView<EditText>(R.id.search_view)
    private val recycler by bindView<RecyclerView>(R.id.recycler_view)
    private val lilWidget by bindView<LilLoaderWidget>(R.id.lil_widget)

    private val adapter = ArrayListDuperAdapter().apply {
        addViewType<Repository, RepositoryWidget>(Repository::class.java)
            .addViewCreator { parent ->
                RepositoryWidget(parent.context).apply {
                    layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                }
            }
            .addViewBinder { widget, item -> widget.bind(item) }
            .addClickListener { _, item -> viewModel.dispatch(RepositoriesEvent.RepositoryClicked(item)) }
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView.addTextChangedListener { text -> viewModel.dispatch(RepositoriesEvent.SearchInputUpdate(text.toString())) }
        recycler.adapter = adapter

        viewModel.observeViewStates(this,
            Observer {
                when (it) {
                    is RepositoriesState.Initial -> showInitialState()
                    is RepositoriesState.Loading -> showLoadingState()
                    is RepositoriesState.Data -> showData(it.repositories)
                    is RepositoriesState.Error -> showError(it.errorMessage)
                }.exhaustive
            })
    }

    //region ui states

    private fun showInitialState() {
        lilWidget.hide()
        recycler.visibility = View.INVISIBLE
    }

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

    private fun showData(repositories: List<Repository>) {
        recycler.visibility = View.VISIBLE
        lilWidget.hide()
        adapter.addAll(repositories)
    }

    //endregion
}
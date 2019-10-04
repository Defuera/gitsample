package ru.justd.githubrepos.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotterknife.bindView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.justd.duperadapter.ArrayListDuperAdapter
import ru.justd.githubrepos.R
import ru.justd.githubrepos.extentions.exhaustive
import ru.justd.githubrepos.repositories.data.Event
import ru.justd.githubrepos.repositories.data.RepositoriesState
import ru.justd.githubrepos.repositories.data.RepositoriesViewModel
import ru.justd.githubrepos.repositories.data.Repository

class RepositoriesFragment : Fragment() {

    private val viewModel by viewModel<RepositoriesViewModel>()

    private val recycler by bindView<RecyclerView>(R.id.recycler_view)

    private val adapter = ArrayListDuperAdapter().apply {
        addViewType<Repository, RepositoryWidget>(Repository::class.java)
            .addViewCreator { parent -> RepositoryWidget(parent.context) }
            .addViewBinder { widget, item -> widget.bind(item) }
            .addClickListener { _, item -> viewModel.dispatch(Event.RepositoryClicked(item)) }
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

        recycler.adapter = adapter

        viewModel.stateHolder.observe(this,
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

    }

    private fun showLoadingState() {

    }

    private fun showError(errorMessage: String) {

    }

    private fun showData(repositories: List<Repository>) {

    }

    //endregion
}
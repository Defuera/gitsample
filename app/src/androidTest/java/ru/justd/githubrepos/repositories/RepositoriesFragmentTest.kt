package ru.justd.githubrepos.repositories

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import ru.justd.githubrepos.MainActivity
import ru.justd.githubrepos.R
import ru.justd.githubrepos.app.BaseViewModel
import ru.justd.githubrepos.repositories.data.RepositoriesEvent
import ru.justd.githubrepos.repositories.data.RepositoriesState
import ru.justd.githubrepos.repositories.data.Repository
import ru.justd.githubrepos.repositories.robots.RepositoriesRobot
import ru.justd.githubrepos.repositories.utils.setState


@RunWith(AndroidJUnit4::class)
class RepositoriesFragmentTest {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java, true, false)

    private val viewModel: BaseViewModel<RepositoriesEvent, RepositoriesState> = mock()

    @Before
    fun setup() {
        loadKoinModules(
            module {
                viewModel(override = true) { viewModel }
            }
        )
    }

    @Test
    fun testLoadingState() {
        rule.launchActivity(Intent())

        viewModel.setState(rule.activity, RepositoriesState.Loading)

        RepositoriesRobot().apply {
            checkScreenDisplayed()
            checkLoaderIsDisplayed()
        }
    }

    @Test
    fun testErrorState() {
        rule.launchActivity(Intent())

        viewModel.setState(rule.activity, RepositoriesState.Error(R.string.network_error))

        RepositoriesRobot().apply {
            checkScreenDisplayed()
            checkErrorIsDisplayed(R.string.network_error)
        }
    }

    @Test
    fun testDataState() {
        rule.launchActivity(Intent())

        viewModel.setState(
            rule.activity, RepositoriesState.Data(
                listOf(
                    Repository("0", "Test repo", true, "description", true, "Clingon", 13, 25)
                )
            )
        )

        RepositoriesRobot().apply {
            checkScreenDisplayed()
            checkRepositoryIsDisplayed( //assume all fields can be tested optionally
                name = "Test Repo",
                fork = true
            )
        }
    }

}
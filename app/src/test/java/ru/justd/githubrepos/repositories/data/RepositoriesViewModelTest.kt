package ru.justd.githubrepos.repositories.data

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.justd.githubrepos.app.Router

@UseExperimental(ExperimentalCoroutinesApi::class)
class RepositoriesViewModelTest {

    private val interactor = mockk<RepositoriesInteractor>(relaxed = true)
    private val router = mockk<Router>(relaxed = true)
    private val stateHolder = mockk<RepositoriesStateHolder>(relaxed = true)

    private val testInstance =
        RepositoriesViewModel(interactor, router, stateHolder, debounceTime = 0)


    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given text changed then repositories fetched`() {
        runBlockingTest {
            // given
            every { stateHolder.setInitialState() }
            every { interactor.getRepositories(any()) } returns Result.Success(listReposStub)

            // when
            testInstance.dispatch(RepositoriesEvent.SearchInputUpdate("dden"))

            // then
            verify { interactor.getRepositories(any()) }
        }
    }

    @Test
    fun `given repository clicked then navigation is invoked`() {
        // given
        val repo = mockk<Repository>()
        every { repo.id } returns "1"
        every { stateHolder.setInitialState() }

        // when
        testInstance.dispatch(RepositoriesEvent.RepositoryClicked(repo))

        // then
        verify { router.navigateToCommitsPage(any(), any()) }
    }

    @Test
    fun `given result success then show data`() {
        // given
        every { stateHolder.setInitialState() }
        every { interactor.getRepositories(any()) } returns Result.Success(listReposStub)

        // when
        testInstance.dispatch(RepositoriesEvent.SearchInputUpdate("defuera"))

        // then
        verify(exactly = 1) { stateHolder.setDataState(listReposStub) }
    }

    @Test
    fun `given result error then show error state`() {
        // given
        every { stateHolder.setInitialState() }
        every { interactor.getRepositories(any()) } returns Result.Error(13)

        // when
        testInstance.dispatch(RepositoriesEvent.SearchInputUpdate("defuera"))

        // then
        verify(exactly = 0) { stateHolder.setDataState(any()) }
        verify(exactly = 1) { stateHolder.setErrorState(13) }
    }


}


private val listReposStub = listOf(
    Repository(
        "",
        "",
        false,
        null,
        false,
        "Java",
        0,
        0
    )
)
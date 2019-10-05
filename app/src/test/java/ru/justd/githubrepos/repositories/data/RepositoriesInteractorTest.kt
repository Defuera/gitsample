package ru.justd.githubrepos.repositories.data

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import ru.justd.githubrepos.R
import java.io.IOException

class RepositoriesInteractorTest {

    private val dataSource = mockk<RepositoryDataSource>()
    private val testSubject = RepositoriesInteractor(dataSource)

    @Test
    fun `when datasourse returns repositories then return success result`() {
        val repositories = mockk<List<Repository>>()
        every { dataSource.getRepositories(any()) } returns repositories
        every { repositories.isNullOrEmpty() } returns false

        val result = testSubject.getRepositories("username")

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).repository).isEqualTo(repositories)
    }

    @Test
    fun `when datasourse returns empty list then return no data error`() {
        val repositories = mockk<List<Repository>>()
        every { dataSource.getRepositories(any()) } returns repositories
        every { repositories.isNullOrEmpty() } returns true

        val result = testSubject.getRepositories("username")

        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).errorMessage).isEqualTo(R.string.no_data)
    }

    @Test
    fun `when io exception is thrown then return network error`() {
        every { dataSource.getRepositories(any()) } throws IOException()

        val result = testSubject.getRepositories("username")

        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).errorMessage).isEqualTo(R.string.network_error)
    }


    @Test
    fun `when UnexpectedFailure then return generic error`() {
        every { dataSource.getRepositories(any()) } throws UnexpectedFailure()

        val result = testSubject.getRepositories("username")

        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).errorMessage).isEqualTo(R.string.generic_error)
    }

}
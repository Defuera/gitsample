package ru.justd.githubrepos.repositories.data

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.fail
import okhttp3.Response
import okhttp3.ResponseBody
import org.intellij.lang.annotations.Language
import org.junit.Test
import ru.justd.githubrepos.app.network.GithubApi
import java.lang.Exception


class RepositoryDataSourceTest {

    private val api = mockk<GithubApi>()
    private val testInstance = RepositoryDataSource(api)

    @Test
    fun `given response is successful then return list of repositories`() {
        val response = mockk<Response>()
        val body = mockk<ResponseBody>()

        every { api.getUserRepositories(any()) } returns response
        every { response.isSuccessful } returns true
        every { response.body } returns body
        every { body.string() } returns successJson

        val repos = testInstance.getRepositories("")
        assertThat(repos?.size).isEqualTo(2)
        assertThat(repos?.get(0))
            .isEqualTo(
                Repository(
                    id = "201467313",
                    name = "aepp-sdk-go",
                    description = null,
                    private = false,
                    fork = true,
                    language = "Kotlin",
                    stargazers = 1,
                    forks = 2
                )
            )
    }

    @Test
    fun `given response is not successful then throw exception`() {
        val response = mockk<Response>()

        every { api.getUserRepositories(any()) } returns response
        every { response.isSuccessful } returns false

        assertException(UnexpectedResponse::class.java) {
            testInstance.getRepositories("")
        }

    }

    @Test
    fun `given null body then throw exception`() {
        val response = mockk<Response>()

        every { api.getUserRepositories(any()) } returns response
        every { response.isSuccessful } returns true
        every { response.body } returns null

        assertException(UnexpectedResponse::class.java) {
            testInstance.getRepositories("")
        }

    }


}

@Language("Json")
private val successJson = """
[
  {
    "id": 201467313,
    "name": "aepp-sdk-go",
    "full_name": "Defuera/aepp-sdk-go",
    "private": false,
    "fork": true,
    "language": "Kotlin",
    "stargazers": 1,
    "forks": 2
  },
  {
    "id": 201467314,
    "name": "test-name",
    "full_name": "Defuera/test-name",
    "private": true,
    "fork": false,
    "language": "Dart",
    "stargazers": 0,
    "forks": 0
  }
]
""".trimIndent()

inline fun <reified T, R> assertException(exception: Class<T>, assertFunction: (params: Any) -> R) {
    try {
        assertFunction("")
        fail("$exception expected")
    } catch (e: Exception) {
        assertThat(e).isInstanceOf(exception)
    }
}
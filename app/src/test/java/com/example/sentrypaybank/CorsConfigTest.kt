package com.example.sentrypaybank


import com.example.sentrypaybank.backend.remote.data.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var repository: AuthRepository

    @Before
    fun setUp() {

        mockWebServer = MockWebServer()
        mockWebServer.start()

        // 2. Initialize your repository
        repository = AuthRepository(mockWebServer.url("/").toString())
    }

    @After
    fun tearDown() {
        // Shutdown the fake server when finished
        mockWebServer.shutdown()
    }

    @Test
    fun testSuccessfulLogin_returnsToken() = runBlocking {
        // Arrange: Tell the fake server exactly what JSON payload to return
        val fakeJsonResponse = """
            {
                "token": "1234567890",
                "username": "user"
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(fakeJsonResponse))

        // Act: Run your repository login function
        val result = repository.loginUser("user", "password")

        // Assert: Verify it successfully extracts the token
        assertTrue(result.isSuccess)
        assertEquals("1234567890", result.getOrNull())
    }

    @Test
    fun testFailedLogin_returnsFailure() = runBlocking {
        // Arrange: Make the fake server return a 401 Unauthorized error payload
        val fakeErrorResponse = """{"error": "Invalid credentials"}"""
        mockWebServer.enqueue(MockResponse().setResponseCode(401).setBody(fakeErrorResponse))

        // Act: Run the repository login function
        val result = repository.loginUser("wrongUser", "wrongPassword")

        // Assert: Verify your repository correctly catches the error code
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message!!.contains("Status code 401"))
    }
}
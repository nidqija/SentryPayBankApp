package com.example.sentrypaybank.backend.remote.data.repository

import com.example.sentrypaybank.backend.remote.data.LoginRequest
import com.example.sentrypaybank.backend.remote.data.LoginResponse
import com.example.sentrypaybank.backend.remote.data.SentryPayURLHost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AuthRepository(baseURL: String? = null) {


    // create a backup state initialized with a placeholder
    // this acts as a fallback if the fetching username data fails on request
    private val _currentUserName = MutableStateFlow("Sentry User")

    // expose read only stream for composable ui view
    val currentUserName: StateFlow<String> = _currentUserName.asStateFlow()

    private val apiService: SentryPayURLHost = if (baseURL != null) {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(SentryPayURLHost::class.java)
    } else {
        SentryPayURLHost.instance
    }




    suspend fun loginUser(usernameInput: String, userPasswordInput: String): Result<LoginResponse> {
        return try {
            val request = LoginRequest(
                username = usernameInput.trim(),
                password = userPasswordInput
            )

            val response = apiService.loginUser(request)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                _currentUserName.value = usernameInput.trim()
                Result.success(body)
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Login failed"
                Result.failure(Exception("$errorMsg (Status: ${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network Error: ${e.localizedMessage}"))
        }
    }


}

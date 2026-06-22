package com.example.sentrypaybank.backend.remote.data.repository

import com.example.sentrypaybank.backend.remote.data.LoginRequest
import com.example.sentrypaybank.backend.remote.data.SentryPayURLHost
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AuthRepository(baseURL: String? = null) {

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


    suspend fun loginUser(usernameInput: String, userPasswordInput: String): Result<String> {
        return try {
            val request = LoginRequest(
                username = usernameInput,
                password = userPasswordInput
            )

            val response = apiService.loginUser(request)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                Result.success(body.token)
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Login failed"
                Result.failure(Exception("$errorMsg (Status: ${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network Error: ${e.localizedMessage}"))
        }
    }


}

package com.example.sentrypaybank.backend.remote.data.repository


import com.example.sentrypaybank.backend.remote.data.SentryPayURLHost
import com.example.sentrypaybank.backend.remote.data.WalletResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WalletRepository (baseURL : String? = null) {
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


    suspend fun getWalletByUser(userId : Long) : Result<WalletResponse> {
            return try {
                val response = apiService.getWalletByUser(userId)
                val body = response.body()


                if (response.isSuccessful && body != null){
                    Result.success(body)
                } else {
                    val errorMsg = "Error message from terminal : " + response.errorBody()?.string()
                    Result.failure(Exception("$errorMsg (Status: ${response.code()})"))
                }


            } catch (e : Exception){
                Result.failure(Exception("Network error: ${e.message}"))
            }
    }
}

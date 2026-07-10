package com.example.sentrypaybank.backend.remote.data.repository

import com.example.sentrypaybank.backend.remote.data.SentryPayURLHost
import com.example.sentrypaybank.backend.remote.data.TransactionRequest
import com.example.sentrypaybank.backend.remote.data.TransactionResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TransactionRepository(baseURL : String? = null) {
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


    suspend fun postTransactionToUser( senderId : Long ,amount : Double , receiverId : String): Result<TransactionResponse>{


        return try {

            val request = TransactionRequest(
                amount = amount,
                receiverId = receiverId
            )

            val response = apiService.postTransactionToUser(request , senderId)
            val body = response.body()

            if (response.isSuccessful && body != null){

                Result.success(body)

            } else {
                val errorMsg = response.errorBody()?.string() ?: "Login failed"
                Result.failure(Exception("$errorMsg (Status: ${response.code()})"))
            }
        } catch (e: Exception){
            Result.failure(Exception("Network Error: ${e.localizedMessage}"))
        }

    }
}

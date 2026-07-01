package com.example.sentrypaybank.backend.remote.data.repository


import com.example.sentrypaybank.backend.remote.data.SentryPayURLHost
import com.example.sentrypaybank.backend.remote.data.ServicesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceRepository(baseURL : String? = null) {

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


  suspend fun getServices() : Result<ServicesResponse>{


      // return a try catch block
      return try {
          // get the response from apiservice
          val response = apiService.getService()
          // extract the response body from response
          val body = response.body()

          if (response.isSuccessful && body != null) {

              // output the body to grab the metadata
              Result.success(body)
          } else {
              // if fails , create an error message and return back to client
              val errorMsg = "Error message from terminal : " + response.errorBody()?.toString()
              Result.failure(Exception("$errorMsg (Status: ${response.code()})"))

          }
      } catch(e : Exception){
          // return network error if failed to reach the server side repo
            Result.failure(Exception("Network error: ${e.message}"))
      }

    }
}
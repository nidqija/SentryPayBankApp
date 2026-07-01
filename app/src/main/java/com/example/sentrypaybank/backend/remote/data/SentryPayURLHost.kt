package com.example.sentrypaybank.backend.remote.data


import android.app.Service
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET

// define the data transfer objects ( DTO )
// this data class is to be sent to backend via url defined below
data class LoginRequest(val username: String, val password: String)
data class LoginResponse(
    val token: String,
    val antiPhishingName: String
)

data class ServicesResponse (
    val serviceName : String,
    val serviceDesc : String,
    val servicePrice : Double,
    val serviceType : String,
    val renewalPeriod : String,
    val currency : String
)
// defined the interface
interface SentryPayURLHost {

    @POST("api/login") // declare POST request to the api

    // suspend fun forcing the function to run asynchronously
    // without disrupting the UI thread
    suspend fun loginUser(@Body request: LoginRequest) : Response<LoginResponse>


    @GET("api/services")
    suspend fun getService() : Response<ServicesResponse>

    // this is the same as static method
    // instead , it uses companion object
    companion object{
        private const val BASE_URL = "http://10.0.2.2:8080/"

        // declare the variable as immutable ( cannot be changed during run time )
        val instance : SentryPayURLHost by lazy { // declare the variable to be ran thread safe
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL) // parse the url to the builder
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build() // build the retrofit engine
                .create(SentryPayURLHost::class.java) // generate the execution
        }
    }
}

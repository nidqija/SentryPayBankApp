package com.example.sentrypaybank.backend.remote.data


import android.app.Service
import com.example.sentrypaybank.pages.ServicesItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import com.google.gson.annotations.SerializedName
import retrofit2.http.Path

// define the data transfer objects ( DTO )
// this data class is to be sent to backend via url defined below
data class LoginRequest(val username: String, val password: String)



data class LoginResponse(
    val token: String,
    val antiPhishingName: String,
    val userId: Long,
    val userName: String // must match the exact data payload variables from backend
)

data class TransactionRequest(
    val receiverId: String,
    val amount: Double
)

data class TransactionResponse(
    val success: Boolean,
    val transactionId: String?,
    val message: String,
    val timestamp: String
)



data class ServicesResponse (
    val serviceName : String,
    val serviceDesc : String,
    val servicePrice : Double,
    val serviceType : String,
    val renewalPeriod : String,
    val currency : String,
    // create a val for a list to store the data object params
    @SerializedName("services")
    val services : List<ServicesItem>
)

data class WalletResponse(
    val walletId: Long,
    val balance: Float,
    val currency: String,
    val createdAt: String, // Or use LocalDateTime depending on your Kotlin serialization setup
    val userId: Long,
    val userFullname: String
)


data class UserResponse(
    val userDetails: List<UserDetails>
) {
    data class UserDetails(
        val userId: Long,
        val userName: String,
        val userEmail: String,
        val userFullName: String,
        val userPhoneNumber: String
    )
}


data class ServiceSubscriptionResponse(
    // Takes a list of SubscriptionDetails objects
    // We use a list because a user can have multiple service subscriptions
    val serviceSubscriptions: List<SubscriptionDetails>
) {

    // Nested data class representing the specific details of a subscription
    data class SubscriptionDetails(
        val subscriptionId: Long,
        val serviceName: String,
        val subscriptionStatus: String,
        val subscriptionType: String,
        val subscriptionStartDate: String,
        val subscriptionEndDate: String? // Nullable String to match Java's potential null check
    )
}






// defined the interface
interface SentryPayURLHost {

    @POST("api/login") // declare POST request to the api

    // suspend fun forcing the function to run asynchronously
    // without disrupting the UI thread
    suspend fun loginUser(@Body request: LoginRequest) : Response<LoginResponse>


    @POST("api/user-transactions/{senderId}")
    suspend fun postTransactionToUser(@Body request: TransactionRequest , @Path("senderId") senderId : Long) : Response<TransactionResponse>



    @GET("api/services")
    suspend fun getService() : Response<ServicesResponse>

    @GET("api/users/{userId}/wallet")
    suspend fun getWalletByUser(@Path("userId") userId: Long) : Response<WalletResponse>

    @GET("api/users/{userId}/user-services")
    suspend fun getServicesByUser(@Path("userId") userId : Long) : Response<ServiceSubscriptionResponse>



    @GET("api/get-users")
    suspend fun fetchUsers() : Response<UserResponse>


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

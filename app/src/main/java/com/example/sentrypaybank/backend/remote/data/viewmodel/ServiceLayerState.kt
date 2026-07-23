package com.example.sentrypaybank.backend.remote.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sentrypaybank.backend.remote.data.ServicesResponse
import com.example.sentrypaybank.backend.remote.data.repository.ServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



// create a sealed interface to prevent illegal states
// and forces the ui to handle the states exhaustively
sealed interface ServiceLayerState {

    // create a loading object state
    object Loading : ServiceLayerState

    // create a success data payload
    data class Success( val data : ServicesResponse) : ServiceLayerState

    // create a error data payload
    data class Error(val message: String) : ServiceLayerState
}


class ServiceViewModel(

    // Dependency Injection: Injecting the repository data layer.
    // Best Practice: In production, consider passing this via constructor injection using Hilt/Koin.
    private val serviceRepository: ServiceRepository = ServiceRepository()

) : ViewModel(){

    // create a private mutable ui state to avoid being modified by composable ui view
    private val _uiState = MutableStateFlow<ServiceLayerState>(ServiceLayerState.Loading)

    // create an immutable state to let the composable view ui sees this as a read only variable
    val uiState : StateFlow<ServiceLayerState> = _uiState.asStateFlow()


    // block triggers an automatic data fetch as soon as this view model is instantiated
    init{
        getServices()
    }



    fun getServices() {
        // launch a coroutine scoped tightly to view model lifecycle
        viewModelScope.launch{
            // immediately broadcast the loading state to display a loading indicator
            _uiState.value = ServiceLayerState.Loading

            // fetch the data from the getServices function from kotlin server side
            serviceRepository.getServices()

                // update the ui state if the fetch is successful
                .onSuccess {
                    response ->
                    _uiState.value = ServiceLayerState.Success(response)
                }

                // update the ui state if the fetch is failed
                .onFailure {
                    exception -> _uiState.value = ServiceLayerState.Error(exception.message ?: "Unknown error")
                }
        }
    }


    // create another layer of abstraction to avoid coupling between repository and view
    fun subscribeToService(userId : Long? , serviceId : String , onResult : (Boolean) -> Unit){
        println("ServiceViewModel: Subscribing user $userId to service $serviceId")
        viewModelScope.launch {
            val result = serviceRepository.startServiceSubscription(userId , serviceId)

            if(result.isSuccess){
                println("ServiceViewModel: Subscription success")
                onResult(true)
            } else {
                println("ServiceViewModel: Subscription failed: ${result.exceptionOrNull()?.message}")
                onResult(false)
            }
        }
    }
}


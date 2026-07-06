package com.example.sentrypaybank.backend.remote.data.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sentrypaybank.backend.remote.data.repository.AuthRepository
import com.example.sentrypaybank.backend.remote.data.repository.WalletRepository
import com.example.sentrypaybank.backend.remote.data.repository.ServiceRepository
import com.example.sentrypaybank.backend.remote.data.ServiceSubscriptionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel(
    private val authRepository: AuthRepository ,
    private val walletRepository: WalletRepository,
    private val serviceRepository: ServiceRepository
): ViewModel(){
    val loggedInUsername : StateFlow<String> = authRepository.currentUserName
    val loggedInUserId : StateFlow<Long?> = authRepository.currentUserId
    private val _currentBalance = MutableStateFlow(0.0f)
    val currentBalance: StateFlow<Float> = _currentBalance.asStateFlow()

    private val _userSubscriptions = MutableStateFlow<ServiceSubscriptionResponse?>(null)
    val userSubscriptions : StateFlow<ServiceSubscriptionResponse?> = _userSubscriptions.asStateFlow()



    private val _errorState = MutableStateFlow<String?>(null)
    val errorState : StateFlow<String?> = _errorState.asStateFlow()

    fun fetchUserWallet(userId : Long){
        viewModelScope.launch{
            walletRepository.getWalletByUser(userId)

                .onSuccess { walletResponse ->
                    _currentBalance.value = walletResponse.balance
                    _errorState.value = null
                }

                .onFailure { exception ->
                    _errorState.value = exception.message
                }

        }
    }


    fun fetchUserSubscriptions(userId : Long){
        viewModelScope.launch {
            serviceRepository.getServicesById(userId)
                .onSuccess { subscriptionResponse ->
                    _userSubscriptions.value = subscriptionResponse
                    _errorState.value = null
                }

                .onFailure { exception ->
                    _userSubscriptions.value = null
                    _errorState.value = exception.message
                }
        }

    }
}

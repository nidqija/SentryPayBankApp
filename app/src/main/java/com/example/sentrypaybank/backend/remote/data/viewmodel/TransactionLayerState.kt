package com.example.sentrypaybank.backend.remote.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sentrypaybank.backend.remote.data.repository.AuthRepository
import androidx.lifecycle.viewModelScope
import com.example.sentrypaybank.backend.remote.data.ServiceSubscriptionResponse
import com.example.sentrypaybank.backend.remote.data.TransactionHistoryResponse
import com.example.sentrypaybank.backend.remote.data.UserResponse
import com.example.sentrypaybank.backend.remote.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionLayerModel(
    private val authRepository: AuthRepository,
    private val transactionRepository: TransactionRepository

    ): ViewModel() {

    val loggedInUserId : StateFlow<Long?> = authRepository.currentUserId

    // change the loggedin user id from any type to long time to be parsed to request dto class
    val userId : Long? get() = loggedInUserId.value



    private val _userContactLists = MutableStateFlow<UserResponse?>(null)
    private val _errorState = MutableStateFlow<String?>(null)


    val userContactLists : StateFlow<UserResponse?> = _userContactLists.asStateFlow()
    val errorState : StateFlow<String?> = _errorState.asStateFlow()
    private val _userTransactionHistory = MutableStateFlow<List<TransactionHistoryResponse>>(emptyList())
    val userTransactionHistory: StateFlow<List<TransactionHistoryResponse>> = _userTransactionHistory.asStateFlow()

    init{
        fetchUserList()
    }

    // create a helper function to fetch the updated list at runtime
    fun fetchUserList() {
        viewModelScope.launch {
            authRepository.fetchUsers()

                .onSuccess { userResponse ->

                    _userContactLists.value = userResponse
                    _errorState.value = null



                }


                .onFailure { exception ->
                    _errorState.value = exception.message
                }
        }

    }



    fun fetchTransactionHistory(userId :Long){
        viewModelScope.launch {
            transactionRepository.fetchTransactionHistory(userId)


                .onSuccess { historyResponse ->
                    _userTransactionHistory.value = historyResponse
                    _errorState.value = null

                }

                .onFailure { exception ->
                    _errorState.value = exception.message

                }
        }
    }







}

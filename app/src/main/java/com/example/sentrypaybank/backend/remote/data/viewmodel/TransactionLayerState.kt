package com.example.sentrypaybank.backend.remote.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sentrypaybank.backend.remote.data.repository.AuthRepository
import androidx.lifecycle.viewModelScope
import com.example.sentrypaybank.backend.remote.data.ServiceSubscriptionResponse
import com.example.sentrypaybank.backend.remote.data.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionLayerModel(
    private val authRepository: AuthRepository

    ): ViewModel() {

// TBA

    private val _userContactLists = MutableStateFlow<UserResponse?>(null)
    private val _errorState = MutableStateFlow<String?>(null)


    val userContactLists : StateFlow<UserResponse?> = _userContactLists.asStateFlow()
    val errorState : StateFlow<String?> = _errorState.asStateFlow()


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







}

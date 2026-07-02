package com.example.sentrypaybank.backend.remote.data.viewmodel


import androidx.lifecycle.ViewModel
import com.example.sentrypaybank.backend.remote.data.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow


class MainViewModel(private val authRepository: AuthRepository): ViewModel(){
    val loggedInUsername : StateFlow<String> = authRepository.currentUserName
}

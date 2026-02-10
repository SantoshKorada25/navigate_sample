package com.santosh.navigate_sample

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

class DataViewModel (private val userRepository: UserRepository = UserRepository() ) : ViewModel() {
    var user by mutableStateOf<User?>(null)
        private set
    fun loadUser(userId:Int) {
        user = userRepository.getUserById(userId)
    }
}
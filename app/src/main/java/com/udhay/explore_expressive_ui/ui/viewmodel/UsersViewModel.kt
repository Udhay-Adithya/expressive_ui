package com.udhay.explore_expressive_ui.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udhay.explore_expressive_ui.data.Result
import com.udhay.explore_expressive_ui.data.users.UsersRepository
import com.udhay.explore_expressive_ui.model.MockUsers
import com.udhay.explore_expressive_ui.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UsersRepository
) : ViewModel() {

    private val _usersState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val usersState: StateFlow<UsersUiState> = _usersState.asStateFlow()

    private val _userState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val userState: StateFlow<UserUiState> = _userState.asStateFlow()

    fun fetchAllUsers() {
        viewModelScope.launch {
            _usersState.value = UsersUiState.Loading
            when (val result = repository.getPostsFeed()) {
                is Result.Success -> {
                    _usersState.value = UsersUiState.Success(result.data)
                }
                is Result.Error -> {
                    _usersState.value = UsersUiState.Error(result.exception.message ?: "Unknown error")
                }
            }
        }
    }

    fun fetchUser(userId: String) {
        viewModelScope.launch {
            _userState.value = UserUiState.Loading
            when (val result = repository.getPost(userId)) {
                is Result.Success -> {
                    _userState.value = UserUiState.Success(result.data)
                }
                is Result.Error -> {
                    _userState.value = UserUiState.Error(result.exception.message ?: "Unknown error")
                }
            }
        }
    }
}

sealed class UsersUiState {
    object Loading : UsersUiState()
    data class Success(val users: MockUsers) : UsersUiState()
    data class Error(val message: String) : UsersUiState()
}

sealed class UserUiState {
    object Loading : UserUiState()
    data class Success(val user: User) : UserUiState()
    data class Error(val message: String) : UserUiState()
}
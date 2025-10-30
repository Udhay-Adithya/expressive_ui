package com.udhay.explore_expressive_ui.data.users.impl

import com.udhay.explore_expressive_ui.data.users.UsersRepository
import com.udhay.explore_expressive_ui.data.users.api.UsersApiService
import com.udhay.explore_expressive_ui.model.MockUsers
import com.udhay.explore_expressive_ui.model.User
import com.udhay.explore_expressive_ui.data.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: UsersApiService) : UsersRepository{
    override suspend fun getPost(userId: String?): Result<User> {
        return try {
            val response = apiService.getUserById(userId ?: "1")
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(Exception("Failed to fetch user: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPostsFeed(): Result<MockUsers> {
        return try {
            val response = apiService.getAllUsers()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(Exception("Failed to fetch users: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
package com.udhay.explore_expressive_ui.data.users

import com.udhay.explore_expressive_ui.model.MockUsers
import com.udhay.explore_expressive_ui.model.User
import com.udhay.explore_expressive_ui.data.Result

interface UsersRepository {
    /**
     * Get a specific User.
     */
    suspend fun getUser(userId: String?): Result<User>

    /**
     * Get all users.
     */
    suspend fun getAllUsers(): Result<MockUsers>

}
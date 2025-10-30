package com.udhay.explore_expressive_ui.data.users

import com.udhay.explore_expressive_ui.model.MockUsers
import com.udhay.explore_expressive_ui.model.User
import com.udhay.explore_expressive_ui.data.Result

interface UsersRepository {
    /**
     * Get a specific User.
     */
    suspend fun getPost(userId: String?): Result<User>

    /**
     * Get all users.
     */
    suspend fun getPostsFeed(): Result<MockUsers>

}
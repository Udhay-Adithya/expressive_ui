package com.udhay.explore_expressive_ui.data.users.api

import com.udhay.explore_expressive_ui.model.MockUsers
import com.udhay.explore_expressive_ui.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApiService {
    @GET("users")
    suspend fun getAllUsers(): Response<MockUsers>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: String): Response<User>
}

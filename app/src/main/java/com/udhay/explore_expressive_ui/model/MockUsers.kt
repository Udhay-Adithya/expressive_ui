package com.udhay.explore_expressive_ui.model

data class MockUsers(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val users: List<User>
)
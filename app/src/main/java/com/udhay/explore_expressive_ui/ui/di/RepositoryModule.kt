package com.udhay.explore_expressive_ui.ui.di

import com.udhay.explore_expressive_ui.data.users.UsersRepository
import com.udhay.explore_expressive_ui.data.users.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ) : UsersRepository
}
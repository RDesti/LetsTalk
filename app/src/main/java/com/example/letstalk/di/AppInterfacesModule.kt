package com.example.letstalk.di

import com.example.letstalk.repositories.IUserDataRepository
import com.example.letstalk.repositories.UserDataRepository
import com.example.letstalk.usecases.IRegistrationUseCase
import com.example.letstalk.usecases.RegistrationUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppInterfacesModule {
    @Binds
    abstract fun bindRegistrationUseCase(impl: RegistrationUseCase): IRegistrationUseCase

    @Binds
    abstract fun bindUserDataRepository(impl: UserDataRepository): IUserDataRepository
}
package com.example.letstalk.usecases

import com.example.letstalk.entity.RequestResultData
import com.example.letstalk.entity.UserData
import kotlinx.coroutines.flow.Flow

interface IRegistrationUseCase {
    suspend fun register(userData: UserData): Flow<RequestResultData>
    fun getUserData(): UserData
    fun clearUserData ()
}
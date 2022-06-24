package com.example.letstalk.usecases

import com.example.letstalk.entity.RequestResultData
import kotlinx.coroutines.flow.Flow

interface ILoginUseCase {
    suspend fun execute(email: String, password: String): Flow<RequestResultData>
}
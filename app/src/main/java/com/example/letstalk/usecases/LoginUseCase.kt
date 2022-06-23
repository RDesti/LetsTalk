package com.example.letstalk.usecases

import com.example.letstalk.entity.RequestResultData
import com.example.letstalk.enum.EResultLoginType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LoginUseCase @Inject constructor() {
    suspend fun execute(email: String, password: String): Flow<RequestResultData> {
        return flowOf(RequestResultData(EResultLoginType.SUCCESS))
    //TODO
    //login(email, password)
    }
}
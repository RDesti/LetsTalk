package com.example.letstalk.usecases

import com.example.letstalk.entity.RequestResultData
import com.example.letstalk.entity.UserData
import com.example.letstalk.enum.EResultLoginType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RegistrationUseCase @Inject constructor() {
    suspend fun register(userData: UserData): Flow<RequestResultData>{
        return flowOf(RequestResultData(EResultLoginType.SUCCESS))
        //todo
    }

    fun getUserData(): UserData {
        return UserData()
    }

    fun clearUserData () {
        //todo
    }
}
package com.example.letstalk.usecases

import com.example.letstalk.entity.RequestResultData
import com.example.letstalk.entity.UserData
import com.example.letstalk.enum.EResultLoginType
import com.example.letstalk.repositories.IUserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val userDataRepository: IUserDataRepository
    ) : IRegistrationUseCase {
    override suspend fun register(userData: UserData): Flow<RequestResultData>{
        return flowOf(RequestResultData(EResultLoginType.SUCCESS))
        //todo
    }

    override fun getUserData(): UserData {
        return userDataRepository.userData
    }

    override fun clearUserData () {
        userDataRepository.clearData()
    }
}
package com.example.letstalk.repositories

import com.example.letstalk.entity.UserData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor() : IUserDataRepository{
    override var userData: UserData = UserData()

    override fun clearData() {
        userData.email = null
        userData.name = null
        userData.secondName = null
        userData.password = null
    }
}
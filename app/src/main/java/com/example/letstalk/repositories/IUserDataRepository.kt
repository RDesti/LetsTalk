package com.example.letstalk.repositories

import com.example.letstalk.entity.UserData

interface IUserDataRepository {
    var userData: UserData
    fun clearData()
}
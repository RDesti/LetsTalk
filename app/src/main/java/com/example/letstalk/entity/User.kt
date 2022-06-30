package com.example.letstalk.entity

data class User(
    val id: String = "",
    val username: String = "",
    val userlastname: String = "",
    val email: String = "",
    var status: String = "",

    var text: String = "",
    var type: String = "",
    var from: String = "",
    var timestamp: Any = ""
)

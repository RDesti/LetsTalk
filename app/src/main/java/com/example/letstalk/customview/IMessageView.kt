package com.example.letstalk.customview

interface IMessageView {
    val id: String
    val from: String
    val timestamp: String
    val fileUrl: String
    val text: String

    companion object {
        val MESSAGE_IMAGE: Int
            get() = 0
        val MESSAGE_TEXT: Int
            get() = 1
    }

    fun getTypeView(): Int
}
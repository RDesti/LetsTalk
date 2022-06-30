package com.example.letstalk.customview

import com.example.letstalk.entity.UserModel
import com.example.letstalk.utilits.TYPE_IMAGE

class CustomViewFactory {
    companion object {
        fun getView(message: UserModel): IMessageView {
            return when (message.type) {
                TYPE_IMAGE -> {
                    ImageMessageView(
                        message.id,
                        message.from,
                        message.timestamp.toString(),
                        message.imageUrl
                    )
                }
                else -> TextMessageView(
                    message.id,
                    message.from,
                    message.timestamp.toString(),
                    message.text
                )
            }
        }
    }
}
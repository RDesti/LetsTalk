package com.example.letstalk.customview

class ImageMessageView(
    override val id: String,
    override val from: String,
    override val timestamp: String,
    override val fileUrl: String,
    override val text: String = ""
) : IMessageView {
    override fun getTypeView(): Int {
        return IMessageView.MESSAGE_IMAGE
    }

    override fun equals(other: Any?): Boolean {
        return (other as IMessageView).id == id
    }
}
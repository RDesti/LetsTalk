package com.example.letstalk.customview

data class TextMessageView(
    override val id: String,
    override val from: String,
    override val timestamp: String,
    override val text: String,
    override val fileUrl: String = ""
) : IMessageView {
    override fun getTypeView(): Int {
        return IMessageView.MESSAGE_TEXT
    }

    override fun equals(other: Any?): Boolean {
        return (other as IMessageView).id == id
    }
}
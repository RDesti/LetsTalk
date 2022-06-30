package com.example.letstalk.adapters.viewholders

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.letstalk.R

class TextMessageViewHolder(view: View):RecyclerView.ViewHolder(view) {
    var blockUserMessage: ConstraintLayout? = null
    var chatUserMessage: TextView? = null
    var chatUserMessageTime: TextView? = null
    var blockReceivedMessage: ConstraintLayout? = null
    var chatReceivedMessage: TextView? = null
    var chatReceivedMessageTime: TextView? = null

    init {
        blockUserMessage = itemView.findViewById(R.id.block_user_message)
        chatUserMessage = itemView.findViewById(R.id.chat_user_message)
        chatUserMessageTime = itemView.findViewById(R.id.chat_user_message_time)
        blockReceivedMessage = itemView.findViewById(R.id.block_receiver_message)
        chatReceivedMessage = itemView.findViewById(R.id.chat_receiver_message)
        chatReceivedMessageTime = itemView.findViewById(R.id.chat_receiver_message_time)
    }
}
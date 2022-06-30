package com.example.letstalk.adapters.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.letstalk.R

class ImageMessageHolder(view: View) : RecyclerView.ViewHolder(view) {
    var blockUserImageMessage: ConstraintLayout? = null
    var chatUserImageMessage: ImageView? = null
    var chatUserImageMessageTime: TextView? = null
    var blockReceivedImageMessage: ConstraintLayout? = null
    var chatReceivedImageMessage: ImageView? = null
    var chatReceivedImageMessageTime: TextView? = null

    init {
        blockUserImageMessage = itemView.findViewById(R.id.block_user_image_message)
        chatUserImageMessage = itemView.findViewById(R.id.chat_user_image)
        chatUserImageMessageTime = itemView.findViewById(R.id.chat_user_image_message_time)
        blockReceivedImageMessage = itemView.findViewById(R.id.block_receiver_image_message)
        chatReceivedImageMessage = itemView.findViewById(R.id.chat_receiver_image)
        chatReceivedImageMessageTime =
            itemView.findViewById(R.id.chat_receiver_image_message_time)
    }
}
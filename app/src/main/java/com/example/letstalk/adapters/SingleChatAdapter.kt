package com.example.letstalk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.letstalk.R
import com.example.letstalk.entity.UserModel
import com.example.letstalk.utilits.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatViewHolder>() {
    private var listMessagesCache = mutableListOf<UserModel>()

    class SingleChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // TEXT_MESSAGE
        var blockUserMessage: ConstraintLayout? = null
        var chatUserMessage: TextView? = null
        var chatUserMessageTime: TextView? = null
        var blockReceivedMessage: ConstraintLayout? = null
        var chatReceivedMessage: TextView? = null
        var chatReceivedMessageTime: TextView? = null

        //IMAGE_MESSAGE
        var blockUserImageMessage: ConstraintLayout? = null
        var chatUserImageMessage: ImageView? = null
        var chatUserImageMessageTime: TextView? = null
        var blockReceivedImageMessage: ConstraintLayout? = null
        var chatReceivedImageMessage: ImageView? = null
        var chatReceivedImageMessageTime: TextView? = null

        init {
            blockUserMessage = itemView.findViewById(R.id.block_user_message)
            chatUserMessage = itemView.findViewById(R.id.chat_user_message)
            chatUserMessageTime = itemView.findViewById(R.id.chat_user_message_time)
            blockReceivedMessage = itemView.findViewById(R.id.block_receiver_message)
            chatReceivedMessage = itemView.findViewById(R.id.chat_receiver_message)
            chatReceivedMessageTime = itemView.findViewById(R.id.chat_receiver_message_time)

            blockUserImageMessage = itemView.findViewById(R.id.block_user_image_message)
            chatUserImageMessage = itemView.findViewById(R.id.chat_user_image)
            chatUserImageMessageTime = itemView.findViewById(R.id.chat_user_image_message_time)
            blockReceivedImageMessage = itemView.findViewById(R.id.block_receiver_image_message)
            chatReceivedImageMessage = itemView.findViewById(R.id.chat_receiver_image)
            chatReceivedImageMessageTime =
                itemView.findViewById(R.id.chat_receiver_image_message_time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return SingleChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: SingleChatViewHolder, position: Int) {
        when (listMessagesCache[position].type) {
            TYPE_TEXT -> drawMessageText(holder, position)
            TYPE_IMAGE -> drawMessageImage(holder, position)
        }
    }

    private fun drawMessageText(holder: SingleChatViewHolder, position: Int) {
        holder.blockReceivedImageMessage?.visibility = View.GONE
        holder.blockUserImageMessage?.visibility = View.GONE

        if (listMessagesCache[position].from == UID) {
            holder.blockUserMessage?.visibility = View.VISIBLE
            holder.blockReceivedMessage?.visibility = View.GONE
            holder.chatUserMessage?.text = listMessagesCache[position].text
            holder.chatUserMessageTime?.text =
                listMessagesCache[position].timestamp.toString().asTime()
        } else {
            holder.blockUserMessage?.visibility = View.GONE
            holder.blockReceivedMessage?.visibility = View.VISIBLE
            holder.chatReceivedMessage?.text = listMessagesCache[position].text
            holder.chatReceivedMessageTime?.text =
                listMessagesCache[position].timestamp.toString().asTime()
        }
    }

    private fun drawMessageImage(holder: SingleChatViewHolder, position: Int) {
        holder.blockUserMessage?.visibility = View.GONE
        holder.blockReceivedMessage?.visibility = View.GONE

        if (listMessagesCache[position].from == UID) {
            holder.blockReceivedImageMessage?.visibility = View.GONE
            holder.blockUserImageMessage?.visibility = View.VISIBLE
            holder.chatUserImageMessage?.downloadAndSetImage(listMessagesCache[position].imageUrl)
            holder.chatUserImageMessageTime?.text =
                listMessagesCache[position].timestamp.toString().asTime()
        } else {
            holder.blockReceivedImageMessage?.visibility = View.VISIBLE
            holder.blockUserImageMessage?.visibility = View.GONE
            holder.chatReceivedImageMessage?.downloadAndSetImage(listMessagesCache[position].imageUrl)
            holder.chatReceivedImageMessageTime?.text =
                listMessagesCache[position].timestamp.toString().asTime()
        }
    }

    override fun getItemCount(): Int = listMessagesCache.size

    fun addItemToBottom(
        userModel: UserModel,
        onSuccess: () -> Unit
    ) {
        if (!listMessagesCache.contains(userModel)) {
            listMessagesCache.add(userModel)
            notifyItemInserted(listMessagesCache.size)
        }
        onSuccess()
    }

    fun addItemToTop(
        userModel: UserModel,
        onSuccess: () -> Unit
    ) {
        if (!listMessagesCache.contains(userModel)) {
            listMessagesCache.add(userModel)
            listMessagesCache.sortBy { it.timestamp.toString() }
            notifyItemInserted(0)
        }
        onSuccess()
    }
}
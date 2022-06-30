package com.example.letstalk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.letstalk.R
import com.example.letstalk.entity.UserModel
import com.example.letstalk.utilits.UID
import com.example.letstalk.utilits.asTime

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatViewHolder>() {
    private var listMessagesCache = mutableListOf<UserModel>()

    class SingleChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return SingleChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: SingleChatViewHolder, position: Int) {
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
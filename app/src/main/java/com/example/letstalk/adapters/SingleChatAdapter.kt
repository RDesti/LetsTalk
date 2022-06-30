package com.example.letstalk.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.letstalk.adapters.viewholders.HolderFactory
import com.example.letstalk.adapters.viewholders.ImageMessageHolder
import com.example.letstalk.adapters.viewholders.TextMessageViewHolder
import com.example.letstalk.customview.IMessageView
import com.example.letstalk.utilits.*

class SingleChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listMessagesCache = mutableListOf<IMessageView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HolderFactory.getHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return listMessagesCache[position].getTypeView()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextMessageViewHolder -> drawMessageText(holder, position)
            is ImageMessageHolder -> drawMessageImage(holder, position)
            else -> {}
        }
    }

    private fun drawMessageText(holder: TextMessageViewHolder, position: Int) {
        if (listMessagesCache[position].from == UID) {
            holder.blockUserMessage?.visibility = View.VISIBLE
            holder.blockReceivedMessage?.visibility = View.GONE
            holder.chatUserMessage?.text = listMessagesCache[position].text
            holder.chatUserMessageTime?.text =
                listMessagesCache[position].timestamp.asTime()
        } else {
            holder.blockUserMessage?.visibility = View.GONE
            holder.blockReceivedMessage?.visibility = View.VISIBLE
            holder.chatReceivedMessage?.text = listMessagesCache[position].text
            holder.chatReceivedMessageTime?.text =
                listMessagesCache[position].timestamp.asTime()
        }
    }

    private fun drawMessageImage(holder: ImageMessageHolder, position: Int) {

        if (listMessagesCache[position].from == UID) {
            holder.blockReceivedImageMessage?.visibility = View.GONE
            holder.blockUserImageMessage?.visibility = View.VISIBLE
            holder.chatUserImageMessage?.downloadAndSetImage(listMessagesCache[position].fileUrl)
            holder.chatUserImageMessageTime?.text =
                listMessagesCache[position].timestamp.asTime()
        } else {
            holder.blockReceivedImageMessage?.visibility = View.VISIBLE
            holder.blockUserImageMessage?.visibility = View.GONE
            holder.chatReceivedImageMessage?.downloadAndSetImage(listMessagesCache[position].fileUrl)
            holder.chatReceivedImageMessageTime?.text =
                listMessagesCache[position].timestamp.asTime()
        }
    }

    override fun getItemCount(): Int = listMessagesCache.size

    fun addItemToBottom(
        item: IMessageView,
        onSuccess: () -> Unit
    ) {
        if (!listMessagesCache.contains(item)) {
            listMessagesCache.add(item)
            notifyItemInserted(listMessagesCache.size)
        }
        onSuccess()
    }

    fun addItemToTop(
        item: IMessageView,
        onSuccess: () -> Unit
    ) {
        if (!listMessagesCache.contains(item)) {
            listMessagesCache.add(item)
            listMessagesCache.sortBy { it.timestamp.toString() }
            notifyItemInserted(0)
        }
        onSuccess()
    }
}
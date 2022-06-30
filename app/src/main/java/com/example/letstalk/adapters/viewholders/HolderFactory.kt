package com.example.letstalk.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.letstalk.R
import com.example.letstalk.customview.IMessageView

class HolderFactory {
    companion object {
        fun getHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                IMessageView.MESSAGE_IMAGE -> {
                    val view =
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.message_item_image, parent, false)
                    ImageMessageHolder(view)
                }
                else -> {
                    val view =
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.message_item_text, parent, false)
                    TextMessageViewHolder(view)
                }
            }
        }
    }
}
package com.example.letstalk.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letstalk.R
import com.example.letstalk.databinding.ActivityChatsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatsActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityChatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
    }
}
package com.example.letstalk.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.letstalk.R
import com.example.letstalk.utilits.AUTH
import com.example.letstalk.utilits.initFirebase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initFirebase()
    }
}
package com.example.letstalk.utilits

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(title: String){
    Toast.makeText(this.context,title, Toast.LENGTH_SHORT).show()
}
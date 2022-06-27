package com.example.letstalk.utilits

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(title: String){
    Toast.makeText(this.context,title, Toast.LENGTH_SHORT).show()
}

fun hideKeyboard(activity: Activity) {
    val inputMethodManager: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
}
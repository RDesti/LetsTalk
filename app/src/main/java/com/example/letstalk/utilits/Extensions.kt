package com.example.letstalk.utilits

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.letstalk.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.showToast(title: String) {
    Toast.makeText(this.context, title, Toast.LENGTH_SHORT).show()
}

fun hideKeyboard(activity: Activity) {
    val inputMethodManager: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
}

fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm")
    return timeFormat.format(time)
}

fun ImageView.downloadAndSetImage(url:String){
    Picasso.get()
        .load(url)
        .fit()
        .placeholder(R.drawable.ic_default_image)
        .into(this)
}
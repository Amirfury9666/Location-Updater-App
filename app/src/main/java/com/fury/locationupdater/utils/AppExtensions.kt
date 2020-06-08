package com.fury.locationupdater.utils

import android.content.Context
import android.view.View
import android.widget.Toast
/**
 * Created By Amir Fury on 08/06/20
 * Email fury.amir93@gmail.com
 */
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}
package com.fury.locationupdater.db

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fury.locationupdater.utils.toast
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class LocationEntity(
    var locationName: String,
    var lat: Double,
    var lang: Double,
    var time: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


    companion object {
        @JvmStatic
        @BindingAdapter("setDate")
        fun setDate(view: TextView, date: Long) {
            val simpleDateFormat = SimpleDateFormat("MMM dd,yyyy HH:mm")
            val date = Date(date)
            view.text = simpleDateFormat.format(date).toString()
        }
    }

    fun onLocationClick(view: View) {
        val context = view.context
        val locationUri = Uri.parse("geo:$lat,$lang")
        val mapIntent = Intent(Intent.ACTION_VIEW, locationUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        }else{
            context.toast("Map is not installed")
        }
    }
}
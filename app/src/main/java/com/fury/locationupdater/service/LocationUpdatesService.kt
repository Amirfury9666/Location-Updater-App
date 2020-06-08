package com.fury.locationupdater.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.fury.locationupdater.ui.HomeActivity
import com.fury.locationupdater.R
import com.fury.locationupdater.db.LocationEntity
import com.fury.locationupdater.utils.Constants
import com.fury.locationupdater.viewModel.LocationViewModel
import com.google.android.gms.location.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

private const val UPDATE_LOCATION_INTERVAL = 5000L
private val TAG = LocationUpdatesService::class.java.simpleName

class LocationUpdatesService : Service(), KodeinAware {
    override val kodein: Kodein by kodein()

    private val locationViewModel: LocationViewModel by instance()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onCreate() {
        super.onCreate()
        initData()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        generateLocationNotification()
        startLocatingUpdates()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun initData() {
        locationRequest = LocationRequest.create()
        locationRequest.interval = UPDATE_LOCATION_INTERVAL
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.applicationContext)

    }

    private fun generateLocationNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                Constants.CHANNEL_ID,
                Constants.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationIntent = Intent(this, HomeActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, Constants.LOCATION_REQ_CODE, notificationIntent, 0)
        val notification = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentTitle(getString(R.string.locationUpdatesDisc))
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(Constants.LOCATION_NOTIFICATION_ID, notification)
    }

    private val locationCallBack = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val location = locationResult.lastLocation
            val time = System.currentTimeMillis()
            val locationEntity = LocationEntity(
                locationName = "",
                lat = location.latitude,
                lang = location.longitude,
                time = time
            )
            locationViewModel.insertLocation(locationEntity)
        }
    }


    @SuppressLint("MissingPermission")
    private fun startLocatingUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.myLooper()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallBack)
    }
}
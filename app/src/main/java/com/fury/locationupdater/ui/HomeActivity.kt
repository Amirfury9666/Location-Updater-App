package com.fury.locationupdater.ui

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fury.locationupdater.R
import com.fury.locationupdater.adapter.LocationAdapter
import com.fury.locationupdater.core.BaseActivity
import com.fury.locationupdater.databinding.ActivityHomeBinding
import com.fury.locationupdater.service.LocationUpdatesService
import com.fury.locationupdater.utils.Constants
import com.fury.locationupdater.utils.toast
import com.fury.locationupdater.viewModel.LocationViewModel
import com.fury.locationupdater.viewModelFactory.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
/**
 * Created By Amir Fury on 08/06/20
 * Email fury.amir93@gmail.com
 */
class HomeActivity : BaseActivity<ActivityHomeBinding>(), KodeinAware {

    override val kodein: Kodein by kodein()

    private var isServiceRunning = false
    private val locationAdapter = LocationAdapter()
    private lateinit var locationViewModel: LocationViewModel
    private val _viewModelFactory: ViewModelFactory by instance()
    override val layoutRes: Int get() = R.layout.activity_home

    override fun getToolbar(binding: ActivityHomeBinding): Toolbar? {
        return binding.toolbar
    }

    override fun onActivityReady(instanceState: Bundle?, binding: ActivityHomeBinding) {
        locationViewModel =
            ViewModelProviders.of(this, _viewModelFactory)[LocationViewModel::class.java]
        binding.locationRecycler.adapter = locationAdapter
        locationViewModel.getAllLocations().observe(this, Observer {
            it?.let {
                locationAdapter.submitList(it.reversed())
                binding.locationRecycler.smoothScrollToPosition(0)
            }
        })

        binding.fabButton.setOnClickListener { showActions(isServiceRunning) }
    }

    private fun isPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun askForPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            Constants.LOCATION_REQ_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.LOCATION_REQ_CODE) {
            if (isPermissionGranted()) {
                startLocationUpdates()
            } else {
                askForPermission()
            }
        }
    }

    private fun startLocationUpdates() {
        if (!isPermissionGranted()){
            askForPermission()
            return
        }

        val serviceIntent = Intent(this, LocationUpdatesService::class.java)
        startService(serviceIntent)
        isServiceRunning = true
        toast("Location Service Started")
    }


    private fun stopLocationUpdates() {
        val serviceIntent = Intent(this, LocationUpdatesService::class.java)
        stopService(serviceIntent)
        isServiceRunning = false
        toast("Location Service Stopped")

    }

    private fun showActions(isServiceRunning : Boolean){
        val itemOne = arrayOf("Stop Updating Location","Delete All Locations","Cancel")
        val itemTwo = arrayOf("Start Updating Location","Delete All Locations","Cancel")
        val builder = AlertDialog.Builder(this)
        if (isServiceRunning){
            builder.setItems(itemOne) { dialog, which ->
                when(which){
                    0-> stopLocationUpdates()
                    1-> locationViewModel.deleteLocation()
                    2-> dialog.dismiss()
                }
            }
        }else{
            builder.setItems(itemTwo) { dialog, which ->
                when(which){
                    0-> startLocationUpdates()
                    1-> locationViewModel.deleteLocation()
                    2-> dialog.dismiss()
                }
            }
        }

        builder.create().show()
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    override fun onStart() {
        super.onStart()
        isServiceRunning = isMyServiceRunning(LocationUpdatesService::class.java)
    }
}
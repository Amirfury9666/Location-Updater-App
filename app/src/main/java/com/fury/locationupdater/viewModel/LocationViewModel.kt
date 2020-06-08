package com.fury.locationupdater.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fury.locationupdater.db.LocationEntity
import com.fury.locationupdater.repository.LocationUpdaterRepository
/**
 * Created By Amir Fury on 08/06/20
 * Email fury.amir93@gmail.com
 */
class LocationViewModel(private val repository : LocationUpdaterRepository)  : ViewModel(){

    fun insertLocation(locationEntity: LocationEntity){
        repository.insertLocation(locationEntity)
    }

    fun deleteLocation(){
        repository.deleteLocation()
    }

    fun getAllLocations() : LiveData<List<LocationEntity>>{
        return repository.getAllLocations()
    }
}
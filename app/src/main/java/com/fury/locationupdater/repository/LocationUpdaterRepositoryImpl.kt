package com.fury.locationupdater.repository

import androidx.lifecycle.LiveData
import com.fury.locationupdater.db.LocationDao
import com.fury.locationupdater.db.LocationEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
/**
 * Created By Amir Fury on 08/06/20
 * Email fury.amir93@gmail.com
 */
class LocationUpdaterRepositoryImpl(private val locationDao: LocationDao) : LocationUpdaterRepository {

    override fun insertLocation(locationEntity: LocationEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            locationDao.insertLocation(locationEntity = locationEntity)
        }
    }

    override fun deleteLocation() {
        CoroutineScope(Dispatchers.IO).launch {
            locationDao.deleteAllLocations()
        }
    }

    override fun getAllLocations(): LiveData<List<LocationEntity>> {
        return locationDao.getAllLocations()
    }
}
package com.fury.locationupdater.repository

import androidx.lifecycle.LiveData
import com.fury.locationupdater.db.LocationEntity
/**
 * Created By Amir Fury on 08/06/20
 * Email fury.amir93@gmail.com
 */
interface LocationUpdaterRepository {
    fun insertLocation(locationEntity: LocationEntity)
    fun deleteLocation()
    fun getAllLocations() : LiveData<List<LocationEntity>>
}
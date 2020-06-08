package com.fury.locationupdater.repository

import androidx.lifecycle.LiveData
import com.fury.locationupdater.db.LocationEntity

interface LocationUpdaterRepository {
    fun insertLocation(locationEntity: LocationEntity)
    fun deleteLocation()
    fun getAllLocations() : LiveData<List<LocationEntity>>
}
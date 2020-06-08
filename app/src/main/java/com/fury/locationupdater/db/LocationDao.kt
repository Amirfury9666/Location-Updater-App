package com.fury.locationupdater.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created By Amir Fury on 08/06/20
 * Email fury.amir93@gmail.com
 */
@Dao
interface LocationDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(locationEntity: LocationEntity)

    @Query("DELETE FROM LocationEntity")
    fun deleteAllLocations()

    @Query("SELECT * FROM LocationEntity")
    fun getAllLocations() : LiveData<List<LocationEntity>>
}
package com.fury.locationupdater.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
/**
 * Created By Amir Fury on 08/06/20
 * Email fury.amir93@gmail.com
 */
@Database(entities = [LocationEntity::class] ,version = 1,exportSchema = true)
abstract class LocationDataBase : RoomDatabase(){

    abstract fun locationDao() : LocationDao

    companion object{
        private var instance : LocationDataBase? = null
        private val LOCK  = Any()

        operator fun invoke(context : Context)  = instance?: synchronized(LOCK){
            instance?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) : LocationDataBase{
            return Room.databaseBuilder(context.applicationContext,LocationDataBase::class.java,"location.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
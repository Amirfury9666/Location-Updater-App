package com.fury.locationupdater.core

import android.app.Application
import com.fury.locationupdater.db.LocationDataBase
import com.fury.locationupdater.repository.LocationUpdaterRepository
import com.fury.locationupdater.repository.LocationUpdaterRepositoryImpl
import com.fury.locationupdater.viewModel.LocationViewModel
import com.fury.locationupdater.viewModelFactory.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
/**
 * Created By Amir Fury on 08/06/20
 * Email fury.amir93@gmail.com
 */
class LocationUpdaterApp : Application(),KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@LocationUpdaterApp))
        bind() from singleton { LocationDataBase(instance()) }
        bind() from singleton { instance<LocationDataBase>().locationDao()}
        bind<LocationUpdaterRepository>() with singleton { LocationUpdaterRepositoryImpl(instance()) }
        bind() from singleton { LocationViewModel(instance()) }
        bind() from provider {
            ViewModelFactory(instance())
        }
    }

}
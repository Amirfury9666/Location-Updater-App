package com.fury.locationupdater.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fury.locationupdater.repository.LocationUpdaterRepository
import com.fury.locationupdater.viewModel.LocationViewModel

class ViewModelFactory(private val repository: LocationUpdaterRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(LocationViewModel::class.java) -> LocationViewModel(repository)
                else -> error("Invalid ViewModel")
            }
        } as T
}
package com.godeliveryservices.shop.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godeliveryservices.shop.R
import com.godeliveryservices.shop.models.Rider
import com.godeliveryservices.shop.network.ApiService
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class HomeViewModel : ViewModel() {

    private val _riderLocationMarkers = MutableLiveData<List<MarkerOptions>>()
    val riderLocationMarkers: LiveData<List<MarkerOptions>>
        get() = _riderLocationMarkers

    private val apiService = ApiService.create()
    private var disposable: Disposable? = null

    init {
        updateRidersLocationContinuously()
    }

    private fun updateRidersLocationContinuously() {
        disposable = Observable.interval(0, 60, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe { fetchRidersLocation() }
    }

    private fun fetchRidersLocation() {
        val disposable = apiService.getRiders()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ response ->
                if (response.isSuccessful && response.code() == 200) {
                    viewModelScope.launch {
                        createMarkers(response.body())
                    }
                    //_riders.value = response.body()
                }
            }, { error ->
                error
            })
    }

    private suspend fun createMarkers(riders: List<Rider>?) = withContext(Dispatchers.IO) {
        val markersList = arrayListOf<MarkerOptions>()
        riders?.forEach { rider ->
            val latlng = rider.RecentLocation?.split(",") ?: return@forEach
            markersList.add(
                MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maps_marker)).position(
                    LatLng(latlng[0].toDouble(), latlng[1].toDouble())
                ).title(rider.Name)
            )
        }
        _riderLocationMarkers.postValue(markersList)
    }
}
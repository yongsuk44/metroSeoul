package com.young.presentation.viewmodel

import android.location.Geocoder
import android.location.Location
import androidx.hilt.lifecycle.ViewModelInject
import com.google.android.gms.tasks.Task
import com.young.presentation.consts.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class LocationCurrentViewModel @ViewModelInject constructor(
    private val geocoder: Geocoder
) : BaseViewModel() {

    fun setApplyCurrentLocation(task: Task<Location>): Flow<Pair<Double, Double>> =
        getAddressGroup(task).map { addressGroup ->
            when (addressGroup.size) {
                1 -> addressGroup.first().latitude to addressGroup.first().longitude
                2 -> {
                    val latitudeAverage = (addressGroup[0].latitude + addressGroup[1].latitude) / 2
                    val longitudeAverage = (addressGroup[0].longitude + addressGroup[1].longitude) / 2
                    latitudeAverage to longitudeAverage
                }
                else -> throw NullPointerException("Location Address Group Size Error")
            }
        }

    private fun getAddressGroup(task: Task<Location>) =
        getGoogleServiceLastLocation(task)
            .transform { location ->
                geocoder.getFromLocation(location.latitude, location.longitude, 2)
                    ?.also { addressGroup -> emit(addressGroup) }
                    ?: throw NullPointerException("GeoCoder 경로 값을 가져오지 못함")
            }
            .flowOn(Dispatchers.Default)

    private fun getGoogleServiceLastLocation(task: Task<Location>) = callbackFlow<Location> {
        task.addOnSuccessListener { sendBlocking(it) }
            .addOnFailureListener { close(NullPointerException("Last Location onFailureListener")) }
            .addOnCanceledListener { close(NullPointerException("Last Location onCanceledListener")) }

        awaitClose()

    }.flowOn(Dispatchers.IO)
}
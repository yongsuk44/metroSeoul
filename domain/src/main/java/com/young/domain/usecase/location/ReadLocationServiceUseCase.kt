package com.young.domain.usecase.location

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.young.base.exception.LocationPermissionException
import com.young.domain.model.DomainUserLocationData
import com.young.domain.model.DomainUserLocationData.Companion.fromAddressToUserLocationData
import com.young.domain.usecase.permission.PermissionLocationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

internal typealias ReadLocationServiceBaseUseCase = () -> Flow<DomainUserLocationData>

class ReadLocationServiceUseCase constructor(
    private val geocoder: Geocoder,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val permissionLocationUseCase: PermissionLocationUseCase
) : ReadLocationServiceBaseUseCase {

    @SuppressLint("MissingPermission")
    override fun invoke() = callbackFlow {
        when {
            permissionLocationUseCase() -> {
                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { offer(it) }
                    .addOnFailureListener { close(NullPointerException("Location Read Failed")) }
            }

            else -> close(LocationPermissionException("Permission Error"))
        }
    }
        .transform { location -> emit(getAddressGroup(location)) }
        .map { addressGroup -> addressGroupToTransformUserLocationData(addressGroup) }


    private fun getAddressGroup(location: Location) =
        geocoder.getFromLocation(location.latitude, location.longitude, 2)
            ?: throw NullPointerException("GeoCoder 경로 값을 가져오지 못함")

    private fun addressGroupToTransformUserLocationData(addressGroup: List<Address>): DomainUserLocationData =
        when (addressGroup.size) {
            1 -> addressGroup.first().fromAddressToUserLocationData()
            2 -> {
                val latitudeAverage = (addressGroup[0].latitude + addressGroup[1].latitude) / 2
                val longitudeAverage = (addressGroup[0].longitude + addressGroup[1].longitude) / 2
                DomainUserLocationData(latitudeAverage, longitudeAverage)
            }
            else -> throw NullPointerException("Location Address Group Size Error")
        }

}

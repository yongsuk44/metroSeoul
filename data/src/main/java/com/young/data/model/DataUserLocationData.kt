package com.young.data.model

import android.location.Address
import com.young.domain.model.DomainUserLocationData

class DataUserLocationData(
    val latitude : Double,
    val longitude : Double
) {
    companion object {
        fun DomainUserLocationData.toMapper() = DataUserLocationData(latitude, longitude)
        fun DataUserLocationData.toMapper() = DomainUserLocationData(latitude, longitude)
    }
}
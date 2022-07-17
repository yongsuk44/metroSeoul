package com.young.domain.model

import android.location.Address
import android.location.Location

class DomainUserLocationData(
    val latitude : Double,
    val longitude : Double
) {
    companion object {
        fun Address.fromAddressToUserLocationData() : DomainUserLocationData =
            DomainUserLocationData(latitude , longitude)

        fun Location.toMapper() : DomainUserLocationData =
            DomainUserLocationData(latitude , longitude)
    }
}
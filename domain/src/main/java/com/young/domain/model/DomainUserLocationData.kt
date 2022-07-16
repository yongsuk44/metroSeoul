package com.young.domain.model

import android.location.Address

class DomainUserLocationData(
    val latitude : Double,
    val longitude : Double
) {
    companion object {
        fun Address.fromAddressToUserLocationData() : DomainUserLocationData =
            DomainUserLocationData(latitude , longitude)
    }
}
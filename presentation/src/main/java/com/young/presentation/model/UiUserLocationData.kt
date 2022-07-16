package com.young.presentation.model

import android.location.Address
import com.young.domain.model.DomainUserLocationData

class UiUserLocationData(
    val latitude : Double,
    val longitude : Double
) {
    companion object {
        fun Address.fromAddressToUserLocationData() : UiUserLocationData =
            UiUserLocationData(latitude , longitude)

        fun DomainUserLocationData.toMapper() : UiUserLocationData =
            UiUserLocationData(latitude, longitude)

        fun userLocationDefaultData() = UiUserLocationData(37.5283169,126.9294254)
    }
}
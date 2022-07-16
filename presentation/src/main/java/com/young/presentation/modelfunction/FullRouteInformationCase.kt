package com.young.presentation.modelfunction

import com.young.domain.model.DomainFullRouteInformationBody
import com.young.presentation.model.ListRouteInformation
import kotlinx.coroutines.flow.Flow

interface FullRouteInformationCase {
    fun loadFullRouteInformation(trailKey: String)
    fun insertAllStationCodes(seoulKey: String)
    fun onSearchEditViewClick(value: Boolean)
    fun onStationClick(item: ListRouteInformation)
    fun onSearchEditViewClear()
    fun onPopupWindowViewVisibleCheck(check: Boolean)
}
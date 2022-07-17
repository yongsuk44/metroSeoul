package com.young.domain.usecase.location

import com.young.domain.model.DomainUserLocationData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ReadLocationBaseUseCase {
    fun readLocationService(): Flow<DomainUserLocationData>
}

class ReadLocationUseCase @Inject constructor(

) : ReadLocationBaseUseCase {

    override fun readLocationService(): Flow<DomainUserLocationData> {
        TODO("Not yet implemented")
    }

}

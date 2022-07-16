package com.young.domain.usecase.information

import com.young.domain.repository.AllStationCodesRepository
import com.young.domain.usecase.StationDataBaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ReadStationTelNumberBaseUseCase {
    suspend fun readStationTelNumber(stinCode: String, key: String) : Flow<String>
}

class ReadStationTelNumberUseCase @Inject constructor(
    private val stationCodesRepository: AllStationCodesRepository,
    private val stationDataUseCase: StationDataBaseUseCase
) : ReadStationTelNumberBaseUseCase {

    override suspend fun readStationTelNumber(stinCode: String, key: String): Flow<String> =
        stationCodesRepository.findStationCode(stinCode)
            .flatMapConcat {
                if (it == null) throw NullPointerException("Station Code를 찾지 못함")
                else stationDataUseCase.getStationTelData(key, it.STATION_CD)
            }
            .map {
                it?.first()?.phoneNumber ?: "1544-7788"
            }
}
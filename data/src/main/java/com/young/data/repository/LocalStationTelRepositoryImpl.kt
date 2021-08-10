package com.young.data.repository

import com.young.data.dao.TelDao
import com.young.data.model.LocalStationTel
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainStationTel
import com.young.domain.repository.informationdetail.local.LocalStationTelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalStationTelRepositoryImpl @Inject constructor(
    private val dao: TelDao
) : LocalStationTelRepository {
    override suspend fun insert(items: List<DomainStationTel>) {
        dao.insert(
            BaseMapper.setList(BaseMapper<DomainStationTel, LocalStationTel>()).run {
                this(items)
            }
        )
    }

    override suspend fun getStationTelData(stinCd : String): Flow<DomainStationTel> =
        flowOf(dao.getAllTelData(stinCd))
            .map {
                BaseMapper<LocalStationTel, DomainStationTel>().run {
                    this(it)
                }
            }
}
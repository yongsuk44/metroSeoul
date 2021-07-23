package com.young.data.mapper

import com.young.data.model.FullRouteInformation
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.AllRouteInformation

object LocalToDomainMapper {
    fun List<AllRouteInformation>.DomainToLocal() : List<FullRouteInformation> {
        val mapper = BaseMapper(AllRouteInformation::class, FullRouteInformation::class)
        BaseMapper.setList(mapper).run {
            return this(this@DomainToLocal)
        }
    }

    fun List<FullRouteInformation>.LocalToDomain() : List<AllRouteInformation> =
        BaseMapper.setList(BaseMapper(FullRouteInformation::class , AllRouteInformation::class)).run {
            this(this@LocalToDomain)
        }

}
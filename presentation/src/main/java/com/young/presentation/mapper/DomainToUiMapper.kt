package com.young.presentation.mapper

import com.young.domain.mapper.BaseMapper
import com.young.domain.model.*
import com.young.presentation.model.UiAllRouteInformation
import com.young.presentation.model.UiConvenienceInformation
import com.young.presentation.model.UiTrailTimeTable

object DomainToUiMapper {
    fun DomainTrailTimeTable.DomaionToUi() : UiTrailTimeTable {
        val timeTableMapper = BaseMapper(DomainTrailTimeTable::class, UiTrailTimeTable::class)
        val headerMapper = BaseMapper(Header::class, com.young.presentation.model.Header::class)
        val timeTableBody =
            BaseMapper(TimeTableBody::class, com.young.presentation.model.TimeTableBody::class)

        timeTableMapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(timeTableBody))
        }.run {
            return this(this@DomaionToUi)
        }
    }

    fun DomainAllRouteInformation.DomainToUi() : UiAllRouteInformation {
        val timeTableMapper =
            BaseMapper(DomainAllRouteInformation::class, UiAllRouteInformation::class)
        val headerMapper =
            BaseMapper(Header::class, com.young.presentation.model.Header::class)
        val bodyMapper = BaseMapper(
            AllRouteInformation::class,
            com.young.presentation.model.AllRouteInformation::class
        )

        timeTableMapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(bodyMapper))
        }.run {
            return this(this@DomainToUi)
        }
    }

    fun List<AllRouteInformation>.DomainToUi() : List<com.young.presentation.model.AllRouteInformation> {
        val bodyMapper = BaseMapper(AllRouteInformation::class, com.young.presentation.model.AllRouteInformation::class)

        BaseMapper.setList(bodyMapper).run {
            return this(this@DomainToUi)
        }
    }

    fun DomainConvenienceInformation.DomainToUi() : UiConvenienceInformation {
        val convenienceMapper = BaseMapper(DomainConvenienceInformation::class, com.young.presentation.model.UiConvenienceInformation::class)
        val headerMapper = BaseMapper(com.young.domain.model.Header::class, com.young.presentation.model.Header::class)
        val bodyMapper = BaseMapper(ConvenienceInformationBody::class, com.young.presentation.model.ConvenienceInformationBody::class)

        convenienceMapper.apply {
            register("header", headerMapper)
            register("body", com.young.domain.mapper.BaseMapper.setList(bodyMapper))
        }.run {
            return this(this@DomainToUi)
        }
    }
}
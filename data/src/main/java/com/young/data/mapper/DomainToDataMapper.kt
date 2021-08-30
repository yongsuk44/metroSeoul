package com.young.data.mapper

import com.young.data.model.*
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.*

object DomainToDataMapper {
    fun DomainTrailCodeAndLineCode.DomainToData() : DataTrailCodeAndLineCode =
        BaseMapper<DomainTrailCodeAndLineCode , DataTrailCodeAndLineCode>().run {
            this(this@DomainToData)
        }

    fun DomainStationNameAndMapXY.DomainToData() : DataStationNameAndMapXY {
        val mapper = BaseMapper<DomainStationNameAndMapXY , DataStationNameAndMapXY>()
        return mapper.apply {
            register("trailCodeAndLineCode" , BaseMapper<DomainTrailCodeAndLineCode , DataTrailCodeAndLineCode>())
        }.run {
            this(this@DomainToData)
        }
    }

    fun DomainRow.DomainToData() : DataRow = BaseMapper<DomainRow , DataRow>().run { this(this@DomainToData) }

    fun DomainAllStationCodes.DomainToData(): DataAllStationCodes {
        val rowMapper = BaseMapper<DomainRow, DataRow>()
        val resultMapper = BaseMapper<DomainRESULT, DataRESULT>()
        val serviceMapper =
            BaseMapper<DomainSearchInfoBySubwayNameService, DataSearchInfoBySubwayNameService>().apply {
                register("RESULT", resultMapper)
                register("row", BaseMapper.setList(rowMapper))
            }

        val mainMapper = BaseMapper<DomainAllStationCodes, DataAllStationCodes>().apply {
            register("SearchInfoBySubwayNameService", serviceMapper)
        }

        return mainMapper(this)
    }

    fun DomainFullRouteInformationBody.DomainToData() : DataFullRouteInformationBody =
        BaseMapper<DomainFullRouteInformationBody , DataFullRouteInformationBody>().run {
            this(this@DomainToData)
        }

    fun DomainFullRouteInformation.DomainToData() : DataFullRouteInformation {
        val mapper = BaseMapper<DomainFullRouteInformation , DataFullRouteInformation>()
        val headerMapper = BaseMapper<Header , DataHeader>()
        val bodyMapper = BaseMapper<DomainFullRouteInformationBody , DataFullRouteInformationBody>()

        return mapper.apply {
            register("header" , headerMapper)
            register("body" , BaseMapper.setList(bodyMapper))
        }.run {
            this(this@DomainToData)
        }
    }
}
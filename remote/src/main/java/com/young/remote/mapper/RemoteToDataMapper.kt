package com.young.remote.mapper

import com.young.base.BaseMapper
import com.young.cache.model.*
import com.young.remote.model.*

object RemoteToDataMapper {
    fun RemoteFullRouteInformation.RemoteToData() : DataFullRouteInformation {
        val mapper =
            BaseMapper(
                RemoteFullRouteInformation::class,
                DataFullRouteInformation::class
            )
        val headerMapper = BaseMapper(
            Header::class,
            DataHeader::class
        )
        val bodyMapper = BaseMapper(
            RemoteFullRouteInformationBody::class,
            DataFullRouteInformationBody::class
        )

        return mapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(bodyMapper))
        }.run {
            this(this@RemoteToData)
        }
    }

    fun RemoteAllStationCodes.RemoteToData() : List<DataRow> {
        return this.SearchInfoBySubwayNameService.row.map {
            BaseMapper<RemoteRow , DataRow>().run { this(it) }
        }
    }

    fun RemoteConvenienceInformation.RemoteToData() : DataConvenienceInformation {
        val mapper = BaseMapper<RemoteConvenienceInformation , DataConvenienceInformation>()
        val bodyMapper = BaseMapper<ConvenienceInformationBody , DataConvenienceInformationBody>()
        val headerMapper = BaseMapper(Header::class, DataHeader::class)

        return mapper.apply {
            register("header" , headerMapper)
            register("body", bodyMapper)
        }.run {
            this(this@RemoteToData)
        }
    }

    fun RemotePlatformEntrance.RemoteToData() : DataPlatformEntrance {
        val mapper = BaseMapper<RemotePlatformEntrance , DataPlatformEntrance>()
        val bodyMapper = BaseMapper<PlatformEntranceBody , DataPlatformEntranceBody>()
        val headerMapper = BaseMapper(Header::class, DataHeader::class)

        return mapper.apply {
            register("header" , headerMapper)
            register("body", bodyMapper)
        }.run {
            this(this@RemoteToData)
        }
    }
}
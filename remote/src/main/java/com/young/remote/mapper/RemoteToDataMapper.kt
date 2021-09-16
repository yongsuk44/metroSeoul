package com.young.remote.mapper

import com.young.base.BaseMapper
import com.young.data.model.*
import com.young.remote.model.*

object RemoteToDataMapper {
    fun RemoteLocationTrailData.RemoteData() : DataLocationTrailData {
        val mapper = BaseMapper<RemoteLocationTrailData , DataLocationTrailData>()
        val headerMapper = BaseMapper(Header::class, DataHeader::class)
        val bodyMapper = BaseMapper<RemoteLocationData , DataLocationData>()

        return mapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(bodyMapper))
        }.run {
            this(this@RemoteData)
        }
    }

    fun RemoteStationSeoulTimeTable.RemoteToData() : DataStationSeoulTimeTable {
        val mapper = BaseMapper<RemoteStationSeoulTimeTable , DataStationSeoulTimeTable>()
        val search = BaseMapper<RemoteSearchSTNTimeTableByIDService , DataSearchSTNTimeTableByIDService>()
        val result = BaseMapper<com.young.remote.model.TimeTableRESULT, com.young.data.model.TimeTableRESULT>()
        val body = BaseMapper<RemoteTimeTableRow , DataTimeTableRow>()

        search.apply {
            register("RESULT" , result)
            register("row" , BaseMapper.setList(body))
        }

        return mapper.apply {
            register("SearchSTNTimeTableByIDService" , search)
        }.run {
            this(this@RemoteToData)
        }
    }

    fun RemoteStationTimeTable.RemoteToData() : DataStationTimeTable {
        val mapper =
            BaseMapper(
                RemoteStationTimeTable::class,
                DataStationTimeTable::class
            )
        val headerMapper = BaseMapper(
            Header::class,
            DataHeader::class
        )
        val bodyMapper = BaseMapper(
            RemoteTimeTableBody::class,
            DataTimeTableBody::class
        )

        return mapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(bodyMapper))
        }.run {
            this(this@RemoteToData)
        }
    }
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
            register("body", BaseMapper.setList(bodyMapper))
        }.run {
            this(this@RemoteToData)
        }
    }

    fun RemoteStationTelNumber.RemoteToData() : List<DataStationBody>? {
        val bodyMapper = BaseMapper<RemoteStationBody , DataStationBody>()
        return BaseMapper.setList(bodyMapper).run {
            if (response.body == null) return null
            else this(this@RemoteToData.response.body.items)
        }
    }
}
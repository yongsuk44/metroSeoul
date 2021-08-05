package com.young.data_remote.mapper

import com.young.data_remote.model.*
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainConvenienceInformation
import com.young.domain.model.DomainPlatformEntrance

object RemoteToDomainMapper {

    fun RemoteConvenienceInformation.RemoteToDomain(): DomainConvenienceInformation {
        val convenienceMapper =
            BaseMapper(RemoteConvenienceInformation::class, DomainConvenienceInformation::class)
        val headerMapper = BaseMapper(Header::class, com.young.domain.model.Header::class)
        val bodyMapper = BaseMapper(
            ConvenienceInformationBody::class,
            com.young.domain.model.ConvenienceInformationBody::class
        )

        convenienceMapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(bodyMapper))
        }.run {
            return this(this@RemoteToDomain)
        }
    }

    fun RemotePlatformEntrance.RemoteToDomain(): DomainPlatformEntrance {
        val mapper = BaseMapper<RemotePlatformEntrance, DomainPlatformEntrance>()
        val body = BaseMapper<PlatformEntranceBody, com.young.domain.model.PlatformEntranceBody>()
        val header = BaseMapper(Header::class, com.young.domain.model.Header::class)

        mapper.apply {
            register("header", header)
            register("body", BaseMapper.setList(body))
        }.run {
            return this(this@RemoteToDomain)
        }
    }
}
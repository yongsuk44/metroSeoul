package com.young.data_remote.mapper

import com.young.data_remote.model.ConvenienceInformationBody
import com.young.data_remote.model.RemoteConvenienceInformation
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainConvenienceInformation
import com.young.domain.model.Header

object RemoteToDomainMapper {

    fun RemoteConvenienceInformation.RemoteToDomain() : DomainConvenienceInformation {
        val convenienceMapper = BaseMapper(RemoteConvenienceInformation::class, DomainConvenienceInformation::class)
        val headerMapper = BaseMapper(Header::class, com.young.domain.model.Header::class)
        val bodyMapper = BaseMapper(ConvenienceInformationBody::class, com.young.domain.model.ConvenienceInformationBody::class)

        convenienceMapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(bodyMapper))
        }.run {
            return this(this@RemoteToDomain)
        }
    }
}
package com.young.cache.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.young.cache.Metro.LocationStore
import java.io.InputStream
import java.io.OutputStream

object LocationSerializer : Serializer<LocationStore> {
    override val defaultValue: LocationStore = LocationStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): LocationStore {
        try {
            return LocationStore.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: LocationStore, output: OutputStream) = t.writeTo(output)
}
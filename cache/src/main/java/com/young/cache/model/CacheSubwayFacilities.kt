package com.young.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CacheSubwayFacilities(
    @ColumnInfo(name = "train_reservation_station_status")
    val TrainReservationStationStatus: String,

    @ColumnInfo(name = "meeting_place_status")
    val MeetingPlaceStatus: String,

    @ColumnInfo(name = "unmanned_civil_service_issuing_machine_status")
    val UnmannedCivilServiceIssuingMachineStatus: String,

    @ColumnInfo(name = "cultural_space_status")
    val CulturalSpaceStatus: String,

    @ColumnInfo(name = "breastfeeding_room_status")
    val BreastfeedingRoomStatus: String,

    @ColumnInfo(name = "elevator_status")
    val ElevatorStatus: String,

    @ColumnInfo(name = "station_name")
    val StationName: String,

    @PrimaryKey
    @ColumnInfo(name = "station_number")
    val StationNumber: String,

    @ColumnInfo(name = "external_station_number")
    val ExternalStationNumber: String,

    @ColumnInfo(name = "bicycle_storage_status")
    val BicycleStorageStatus: String,

    @ColumnInfo(name = "metro_linenumber")
    val MetroLineNumber: String,

    @ColumnInfo(name = "transfer_parking_lot_status")
    val TransferParkingLotStatus: String,

    @ColumnInfo(name = "currency_exchange_kiosk_status")
    val CurrencyExchangeKioskStatus: String,

    @ColumnInfo(name = "wheelchair_status")
    val WheelchairStatus: String

)
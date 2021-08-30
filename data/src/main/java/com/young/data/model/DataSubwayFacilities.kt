package com.young.data.model

data class DataSubwayFacilitiesPage(
    val currentCount: Int,
    val data: List<DataSubwayFacilities>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)

data class DataSubwayFacilities(

    val TrainReservationStationStatus: String,

    val MeetingPlaceStatus: String,

    val UnmannedCivilServiceIssuingMachineStatus: String,

    val CulturalSpaceStatus: String,

    val BreastfeedingRoomStatus: String,

    val ElevatorStatus: String,

    val StationName: String,

    val StationNumber: String,

    val ExternalStationNumber: String,

    val BicycleStorageStatus: String,

    val MetroLineNumber: String,

    val TransferParkingLotStatus: String,

    val CurrencyExchangeKioskStatus: String,

    val WheelchairStatus: String
)
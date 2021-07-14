package com.young.presentation.model

import com.google.gson.annotations.SerializedName

data class UiSubwayFacilities(
    @SerializedName("기차예매역여부")
    val TrainReservationStationStatus: String,
    @SerializedName("만남의장소여부")
    val MeetingPlaceStatus: String,
    @SerializedName("무인민원발급기여부")
    val UnmannedCivilServiceIssuingMachineStatus: String,
    @SerializedName("문화공간여부")
    val CulturalSpaceStatus: String,
    @SerializedName("수유실여부")
    val BreastfeedingRoomStatus: String,
    @SerializedName("엘리베이터여부")
    val ElevatorStatus: String,
    @SerializedName("역명")
    val StationName: String,
    @SerializedName("역번호")
    val StationNumber: String,
    @SerializedName("외부역번호")
    val ExternalStationNumber: String,
    @SerializedName("자전거보관소여부")
    val BicycleStorageStatus: String,
    @SerializedName("호선")
    val MetroLineNumber: String,
    @SerializedName("환승주차장여부")
    val TransferParkingLotStatus: String,
    @SerializedName("환전키오스크여부")
    val CurrencyExchangeKioskStatus: String,
    @SerializedName("휠체어리프트여부")
    val WheelchairStatus: String
)
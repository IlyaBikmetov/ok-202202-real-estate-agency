package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgRealtyProperty

enum class AdRealtyProperty {
    FLAT,
    ROOM,
    HOTEL,
    HOSTEL,
    HOUSE,
    OFFICE,
    STORAGE
}

fun AdRealtyProperty?.fromTransport() = when(this) {
    null -> ReAgRealtyProperty.NONE
    AdRealtyProperty.FLAT -> ReAgRealtyProperty.FLAT
    AdRealtyProperty.ROOM -> ReAgRealtyProperty.ROOM
    AdRealtyProperty.HOTEL -> ReAgRealtyProperty.HOTEL
    AdRealtyProperty.HOSTEL -> ReAgRealtyProperty.HOSTEL
    AdRealtyProperty.HOUSE -> ReAgRealtyProperty.HOUSE
    AdRealtyProperty.OFFICE -> ReAgRealtyProperty.OFFICE
    AdRealtyProperty.STORAGE -> ReAgRealtyProperty.STORAGE
}

fun ReAgRealtyProperty.toTransport() = when(this) {
    ReAgRealtyProperty.NONE -> null
    ReAgRealtyProperty.FLAT -> AdRealtyProperty.FLAT
    ReAgRealtyProperty.ROOM -> AdRealtyProperty.ROOM
    ReAgRealtyProperty.HOTEL -> AdRealtyProperty.HOTEL
    ReAgRealtyProperty.HOSTEL -> AdRealtyProperty.HOSTEL
    ReAgRealtyProperty.HOUSE -> AdRealtyProperty.HOUSE
    ReAgRealtyProperty.OFFICE -> AdRealtyProperty.OFFICE
    ReAgRealtyProperty.STORAGE -> AdRealtyProperty.STORAGE
}
package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.*

object ReAgAdRealtyEntity {
    fun toInternal(realtyProperty: AdRealtyProperty, realty: String): IReAgAdRealty {
        return when (realtyProperty) {
            AdRealtyProperty.FLAT -> Json.decodeFromString<ReAgAdRealtyFlatEntity>(realty).toInternal()
            AdRealtyProperty.ROOM -> Json.decodeFromString<ReAgAdRealtyRoomEntity>(realty).toInternal()
            AdRealtyProperty.HOTEL -> Json.decodeFromString<ReAgAdRealtyHotelEntity>(realty).toInternal()
            AdRealtyProperty.HOSTEL -> Json.decodeFromString<ReAgAdRealtyHostelEntity>(realty).toInternal()
            AdRealtyProperty.HOUSE -> Json.decodeFromString<ReAgAdRealtyHouseEntity>(realty).toInternal()
            AdRealtyProperty.OFFICE -> Json.decodeFromString<ReAgAdRealtyOfficeEntity>(realty).toInternal()
            AdRealtyProperty.STORAGE -> Json.decodeFromString<ReAgAdRealtyStorageEntity>(realty).toInternal()
        }
    }
}

internal fun IReAgAdRealty.toEntity(): String? = when(this) {
    is ReAgAdRealtyFlat -> Json.encodeToString(ReAgAdRealtyFlatEntity(this))
    is ReAgAdRealtyRoom -> Json.encodeToString(ReAgAdRealtyRoomEntity(this))
    is ReAgAdRealtyHotel -> Json.encodeToString(ReAgAdRealtyHotelEntity(this))
    is ReAgAdRealtyHostel -> Json.encodeToString(ReAgAdRealtyHostelEntity(this))
    is ReAgAdRealtyHouse -> Json.encodeToString(ReAgAdRealtyHouseEntity(this))
    is ReAgAdRealtyOffice -> Json.encodeToString(ReAgAdRealtyOfficeEntity(this))
    is ReAgAdRealtyStorage -> Json.encodeToString(ReAgAdRealtyStorageEntity(this))
    else -> null
}
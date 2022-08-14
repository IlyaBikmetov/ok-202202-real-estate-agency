package ru.ibikmetov.kotlin.realestateagency.inmemory.entityes

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.*

sealed interface IReAgAdRealtyEntity

fun IReAgAdRealtyEntity?.toInternal() = when(this) {
    is ReAgAdRealtyFlatEntity -> this.toInternal()
    is ReAgAdRealtyHostelEntity -> this.toInternal()
    is ReAgAdRealtyHotelEntity -> this.toInternal()
    is ReAgAdRealtyHouseEntity -> this.toInternal()
    is ReAgAdRealtyOfficeEntity -> this.toInternal()
    is ReAgAdRealtyRoomEntity -> this.toInternal()
    is ReAgAdRealtyStorageEntity -> this.toInternal()
    null -> IReAgAdRealty.NONE
}

internal fun IReAgAdRealty.toEntity() = when(this) {
    is ReAgAdRealtyFlat -> ReAgAdRealtyFlatEntity(this)
    is ReAgAdRealtyHostel -> ReAgAdRealtyHostelEntity(this)
    is ReAgAdRealtyHotel -> ReAgAdRealtyHotelEntity(this)
    is ReAgAdRealtyHouse -> ReAgAdRealtyHouseEntity(this)
    is ReAgAdRealtyOffice -> ReAgAdRealtyOfficeEntity(this)
    is ReAgAdRealtyRoom -> ReAgAdRealtyRoomEntity(this)
    is ReAgAdRealtyStorage -> ReAgAdRealtyStorageEntity(this)
    ReAgAdRealtyNone -> null
    else -> null
}
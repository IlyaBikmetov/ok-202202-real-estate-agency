package ru.ibikmetov.kotlin.realestateagency.inmemory.entityes

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyHotel

data class ReAgAdRealtyHotelEntity(
    var floor: Int,
    var cntRooms: Int,
    var balcony: Boolean,
): IReAgAdRealtyEntity {
    constructor(model: ReAgAdRealtyHotel): this(
        floor = model.floor,
        cntRooms = model.cntRooms,
        balcony = model.balcony
    )
    fun toInternal() = ReAgAdRealtyHotel(
        floor = floor,
        cntRooms = cntRooms,
        balcony = balcony,
    )
}
package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import kotlinx.serialization.Serializable
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyHotel

@Serializable
data class ReAgAdRealtyHotelEntity(
    var floor: Int?,
    var cntRooms: Int?,
    var balcony: Boolean?,
) {
    constructor(model: ReAgAdRealtyHotel): this(
        floor = model.floor,
        cntRooms = model.cntRooms,
        balcony = model.balcony
    )
    fun toInternal() = ReAgAdRealtyHotel(
        floor = floor!!,
        cntRooms = cntRooms!!,
        balcony = balcony!!,
    )
}
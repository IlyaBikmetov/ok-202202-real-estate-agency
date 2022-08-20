package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyStoveType
import kotlinx.serialization.Serializable
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyHouse

@Serializable
data class ReAgAdRealtyHouseEntity(
    var square: Float?,
    var cntFloors: Int?,
    var cntRooms: Int?,
    var stoveType: ReAgAdRealtyStoveType?,
    var balcony: Boolean?,
) {
    constructor(model: ReAgAdRealtyHouse): this(
        square = model.square,
        cntFloors = model.cntFloors,
        cntRooms = model.cntRooms,
        stoveType = model.stoveType,
        balcony = model.balcony
    )
    fun toInternal() = ReAgAdRealtyHouse(
        square = square!!,
        cntFloors = cntFloors!!,
        cntRooms = cntRooms!!,
        stoveType = stoveType!!,
        balcony = balcony!!
    )
}
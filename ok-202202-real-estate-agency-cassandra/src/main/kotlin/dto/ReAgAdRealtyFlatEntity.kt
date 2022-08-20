package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyStoveType
import kotlinx.serialization.Serializable
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyFlat

@Serializable
data class ReAgAdRealtyFlatEntity(
    var square: Float?,
    var floor: Int?,
    var cntRooms: Int?,
    var stoveType: ReAgAdRealtyStoveType?,
    var balcony: Boolean?,
) {
    constructor(model: ReAgAdRealtyFlat): this(
        square = model.square,
        floor = model.floor,
        cntRooms = model.cntRooms,
        stoveType = model.stoveType,
        balcony = model.balcony
    )
    fun toInternal() = ReAgAdRealtyFlat(
        square = square!!,
        floor = floor!!,
        cntRooms = cntRooms!!,
        stoveType = stoveType!!,
        balcony = balcony!!
    )
}
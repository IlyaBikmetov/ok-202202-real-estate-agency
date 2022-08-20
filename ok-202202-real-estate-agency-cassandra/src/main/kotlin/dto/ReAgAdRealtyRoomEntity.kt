package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import kotlinx.serialization.Serializable
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyRoom

@Serializable
data class ReAgAdRealtyRoomEntity(
    var square: Float?,
    var floor: Int?,
    var balcony: Boolean?,
) {
    constructor(model: ReAgAdRealtyRoom): this(
        square = model.square,
        floor = model.floor,
        balcony = model.balcony
    )
    fun toInternal() = ReAgAdRealtyRoom(
        square = square!!,
        floor = floor!!,
        balcony = balcony!!
    )
}
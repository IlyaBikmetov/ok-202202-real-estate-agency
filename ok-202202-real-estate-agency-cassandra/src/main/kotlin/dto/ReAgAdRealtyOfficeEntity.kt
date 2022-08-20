package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import kotlinx.serialization.Serializable
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyOffice

@Serializable
data class ReAgAdRealtyOfficeEntity(
    var square: Float?,
    var floor: Int?,
    var cntRooms: Int?
) {
    constructor(model: ReAgAdRealtyOffice): this(
        square = model.square,
        floor = model.floor,
        cntRooms = model.cntRooms,
    )
    fun toInternal() = ReAgAdRealtyOffice(
        square = square!!,
        floor = floor!!,
        cntRooms = cntRooms!!,
    )
}
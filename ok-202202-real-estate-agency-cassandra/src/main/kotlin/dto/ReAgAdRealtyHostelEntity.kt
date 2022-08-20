package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyStoveType
import kotlinx.serialization.Serializable
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyHostel

@Serializable
data class ReAgAdRealtyHostelEntity(
    var floor: Int?,
    var cntBeds: Int?,
    var stoveType: ReAgAdRealtyStoveType?,
) {
    constructor(model: ReAgAdRealtyHostel): this(
        floor = model.floor,
        cntBeds = model.cntBeds,
        stoveType = model.stoveType,
    )
    fun toInternal() = ReAgAdRealtyHostel(
        floor = floor!!,
        cntBeds = cntBeds!!,
        stoveType = stoveType!!,
    )
}
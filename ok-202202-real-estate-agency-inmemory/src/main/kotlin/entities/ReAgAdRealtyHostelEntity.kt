package ru.ibikmetov.kotlin.realestateagency.inmemory.entities

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyHostel
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyStoveType

data class ReAgAdRealtyHostelEntity(
    var floor: Int,
    var cntBeds: Int,
    var stoveType: ReAgAdRealtyStoveType = ReAgAdRealtyStoveType.NONE,
): IReAgAdRealtyEntity {
    constructor(model: ReAgAdRealtyHostel): this(
        floor = model.floor,
        cntBeds = model.cntBeds,
        stoveType = model.stoveType.takeIf { it != ReAgAdRealtyStoveType.NONE }!!.let { it },
    )
    fun toInternal() = ReAgAdRealtyHostel(
        floor = floor,
        cntBeds = cntBeds,
        stoveType = stoveType,
    )
}
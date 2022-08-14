package ru.ibikmetov.kotlin.realestateagency.inmemory.entityes

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyFlat
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyStoveType

data class ReAgAdRealtyFlatEntity(
    var square: Float,
    var floor: Int,
    var cntRooms: Int,
    var stoveType: ReAgAdRealtyStoveType,
    var balcony: Boolean,
): IReAgAdRealtyEntity {
    constructor(model: ReAgAdRealtyFlat): this(
        square = model.square,
        floor = model.floor,
        cntRooms = model.cntRooms,
        stoveType = model.stoveType.takeIf { it != ReAgAdRealtyStoveType.NONE }!!.let { it },
        balcony = model.balcony
    )
    fun toInternal() = ReAgAdRealtyFlat(
        square = square,
        floor = floor,
        cntRooms = cntRooms,
        stoveType = stoveType,
        balcony = balcony,
    )
}
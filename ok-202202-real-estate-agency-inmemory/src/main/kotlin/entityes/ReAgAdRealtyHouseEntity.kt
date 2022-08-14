package ru.ibikmetov.kotlin.realestateagency.inmemory.entityes

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyHouse
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyStoveType

data class ReAgAdRealtyHouseEntity(
    var square: Float,
    var cntFloors: Int,
    var cntRooms: Int,
    var stoveType: ReAgAdRealtyStoveType = ReAgAdRealtyStoveType.NONE,
    var balcony: Boolean,
): IReAgAdRealtyEntity {
    constructor(model: ReAgAdRealtyHouse): this(
        square = model.square,
        cntFloors = model.cntFloors,
        cntRooms = model.cntRooms,
        stoveType = model.stoveType.takeIf { it != ReAgAdRealtyStoveType.NONE }!!.let { it },
        balcony = model.balcony
    )
    fun toInternal() = ReAgAdRealtyHouse(
        square = square,
        cntFloors = cntFloors,
        cntRooms = cntRooms,
        stoveType = stoveType,
        balcony = balcony,
    )
}
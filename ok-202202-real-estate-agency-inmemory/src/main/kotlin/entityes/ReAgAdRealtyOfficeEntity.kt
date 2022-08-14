package ru.ibikmetov.kotlin.realestateagency.inmemory.entityes

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyHouse
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyOffice
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyStoveType

data class ReAgAdRealtyOfficeEntity(
    var square: Float,
    var floor: Int,
    var cntRooms: Int
): IReAgAdRealtyEntity {
    constructor(model: ReAgAdRealtyOffice): this(
        square = model.square,
        floor = model.floor,
        cntRooms = model.cntRooms,
    )
    fun toInternal() = ReAgAdRealtyOffice(
        square = square,
        floor = floor,
        cntRooms = cntRooms,
    )
}
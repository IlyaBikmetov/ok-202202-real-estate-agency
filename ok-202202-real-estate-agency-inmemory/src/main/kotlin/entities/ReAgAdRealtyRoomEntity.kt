package ru.ibikmetov.kotlin.realestateagency.inmemory.entities

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyRoom

data class ReAgAdRealtyRoomEntity(
    var square: Float,
    var floor: Int,
    var balcony: Boolean,
): IReAgAdRealtyEntity {
    constructor(model: ReAgAdRealtyRoom): this(
        square = model.square,
        floor = model.floor,
        balcony = model.balcony
    )
    fun toInternal() = ReAgAdRealtyRoom(
        square = square,
        floor = floor,
        balcony = balcony,
    )
}
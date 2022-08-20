package ru.ibikmetov.kotlin.realestateagency.inmemory.entities

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyStorage

data class ReAgAdRealtyStorageEntity(
    var square: Float,
    var floor: Int,
): IReAgAdRealtyEntity {
    constructor(model: ReAgAdRealtyStorage): this(
        square = model.square,
        floor = model.floor,
    )
    fun toInternal() = ReAgAdRealtyStorage(
        square = square,
        floor = floor,
    )
}
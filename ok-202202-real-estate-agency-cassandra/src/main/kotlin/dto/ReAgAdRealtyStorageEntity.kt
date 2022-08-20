package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import kotlinx.serialization.Serializable
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyStorage

@Serializable
data class ReAgAdRealtyStorageEntity(
    var square: Float?,
    var floor: Int?,
) {
    constructor(model: ReAgAdRealtyStorage): this(
        square = model.square,
        floor = model.floor,
    )
    fun toInternal() = ReAgAdRealtyStorage(
        square = square!!,
        floor = floor!!,
    )
}
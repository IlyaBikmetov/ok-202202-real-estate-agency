package ru.ibikmetov.kotlin.realestateagency.common.models.realty

data class ReAgAdRealtyRoom(
    var square: Float,
    var floor: Int,
    var balcony: Boolean,

    ): IReAgAdRealty {
    override fun deepCopy(): ReAgAdRealtyRoom = copy()
}
package ru.ibikmetov.kotlin.realestateagency.common.models.realty

data class ReAgAdRealtyHotel(
    var floor: Int,
    var cntRooms: Int,
    var balcony: Boolean,

): IReAgAdRealty {
    override fun deepCopy(): ReAgAdRealtyHotel = copy()
}
package ru.ibikmetov.kotlin.realestateagency.common.models.realty

data class ReAgAdRealtyHouse(
    var square: Float,
    var cntFloors: Int,
    var cntRooms: Int,
    var stoveType: ReAgAdRealtyStoveType = ReAgAdRealtyStoveType.NONE,
    var balcony: Boolean,

    ): IReAgAdRealty {
    override fun deepCopy(): ReAgAdRealtyHouse = copy()
}

package ru.ibikmetov.kotlin.realestateagency.common.models.realty

data class ReAgAdRealtyFlat(
    var floor: Int,
    var square: Float,
    var cntRooms: Int,
    var stoveType: ReAgAdRealtyStoveType = ReAgAdRealtyStoveType.NONE,
    var balcony : Boolean,
): IReAgAdRealty {
    override fun deepCopy(): ReAgAdRealtyFlat = copy()
}
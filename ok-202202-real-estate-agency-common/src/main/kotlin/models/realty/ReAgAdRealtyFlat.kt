package ru.ibikmetov.kotlin.realestateagency.common.models.realty

data class ReAgAdRealtyFlat(
    var square: Float?,
    var floor: Int?,
    var cntRooms: Int?,
    var stoveType: ReAgAdRealtyStoveType? = ReAgAdRealtyStoveType.NONE,
    var balcony: Boolean?,

    ): IReAgAdRealty {
    override fun deepCopy(): ReAgAdRealtyFlat = copy()
}
package ru.ibikmetov.kotlin.realestateagency.common.models.realty

data class ReAgAdRealtyOffice(
    var square: Float,
    var floor: Int,
    var cntRooms: Int

    ): IReAgAdRealty {
    override fun deepCopy(): ReAgAdRealtyOffice = copy()
}
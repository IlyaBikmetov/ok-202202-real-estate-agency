package ru.ibikmetov.kotlin.realestateagency.common.models.realty

data class ReAgAdRealtyHostel(
    var floor: Int,
    var cntBeds: Int,
    var stoveType: ReAgAdRealtyStoveType = ReAgAdRealtyStoveType.NONE,

    ): IReAgAdRealty {
    override fun deepCopy(): ReAgAdRealtyHostel = copy()
}
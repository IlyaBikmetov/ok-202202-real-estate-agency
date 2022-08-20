package ru.ibikmetov.kotlin.realestateagency.common.models.realty

data class ReAgAdRealtyStorage(
    var square: Float,
    var floor: Int,

    ): IReAgAdRealty {
    override fun deepCopy(): ReAgAdRealtyStorage = copy()
}
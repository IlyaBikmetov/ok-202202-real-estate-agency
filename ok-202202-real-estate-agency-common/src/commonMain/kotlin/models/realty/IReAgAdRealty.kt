package ru.ibikmetov.kotlin.realestateagency.common.models.realty

sealed interface IReAgAdRealty {
    fun deepCopy(): IReAgAdRealty
    companion object {
        val NONE = ReAgAdRealtyNone
    }
}

object ReAgAdRealtyNone: IReAgAdRealty {
    override fun deepCopy(): IReAgAdRealty = this
}
package ru.ibikmetov.kotlin.realestateagency.common.models.realty

sealed interface IReAgAdRealty {
    companion object {
        val NONE = ReAgAdRealtyNone
    }
}

object ReAgAdRealtyNone: IReAgAdRealty
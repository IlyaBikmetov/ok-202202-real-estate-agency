package ru.ibikmetov.kotlin.realestateagency.common.models

data class ReAgError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
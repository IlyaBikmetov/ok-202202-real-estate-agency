package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgRentType

enum class AdRentType {
    LONG,
    DAILY,
}

fun AdRentType?.fromTransport() = when(this) {
    null -> ReAgRentType.NONE
    AdRentType.LONG -> ReAgRentType.LONG
    AdRentType.DAILY -> ReAgRentType.DAILY
}

fun ReAgRentType.toTransport() = when(this) {
    ReAgRentType.NONE -> null
    ReAgRentType.LONG -> AdRentType.LONG
    ReAgRentType.DAILY -> AdRentType.DAILY
}
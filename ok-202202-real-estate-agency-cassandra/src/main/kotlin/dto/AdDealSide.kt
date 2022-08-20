package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgDealSide

enum class AdDealSide {
    DEMAND,
    PROPOSAL,
}

fun AdDealSide?.fromTransport() = when(this) {
    null -> ReAgDealSide.NONE
    AdDealSide.DEMAND -> ReAgDealSide.DEMAND
    AdDealSide.PROPOSAL -> ReAgDealSide.PROPOSAL
}

fun ReAgDealSide.toTransport() = when(this) {
    ReAgDealSide.NONE -> null
    ReAgDealSide.DEMAND -> AdDealSide.DEMAND
    ReAgDealSide.PROPOSAL -> AdDealSide.PROPOSAL
}
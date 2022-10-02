package ru.ibikmetov.kotlin.realestateagency.common.models

data class ReAgAdFilter(
    var searchString: String = "",
    var ownerId: ReAgUserId = ReAgUserId.NONE,
    var dealSide: ReAgDealSide = ReAgDealSide.NONE,
    var searchTypes: MutableSet<ReAgSearchTypes> = mutableSetOf(),
)
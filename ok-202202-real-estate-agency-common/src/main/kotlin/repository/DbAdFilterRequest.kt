package ru.ibikmetov.kotlin.realestateagency.common.repository

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgDealSide
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgRentType
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgUserId

data class DbAdFilterRequest(
    val title: String = "",
    val ownerId: ReAgUserId = ReAgUserId.NONE,
    val dealSide: ReAgDealSide = ReAgDealSide.NONE,
    val rentType: ReAgRentType = ReAgRentType.NONE,
)
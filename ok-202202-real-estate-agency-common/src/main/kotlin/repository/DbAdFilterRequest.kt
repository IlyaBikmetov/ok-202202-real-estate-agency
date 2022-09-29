package ru.ibikmetov.kotlin.realestateagency.common.repository

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgDealSide
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgRentType

class DbAdFilterRequest(
    val search: String = "",
    val dealSide: ReAgDealSide = ReAgDealSide.NONE,
    val rentType: ReAgRentType = ReAgRentType.NONE,
) {
    constructor (pSearch: String): this(
        search = pSearch,
        dealSide = ReAgDealSide.values().find { it.name == pSearch.uppercase() } ?: ReAgDealSide.NONE,
        rentType = ReAgRentType.values().find { it.name == pSearch.uppercase() } ?: ReAgRentType.NONE,
    )
}
package ru.ibikmetov.kotlin.realestateagency.common.models

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.IReAgAdRealty

data class ReAgAd(
    var id: ReAgAdId = ReAgAdId.NONE,
    var title: String = "",
    var description: String = "",
    var address: String = "",
    var ownerId: ReAgUserId = ReAgUserId.NONE,
    var visibility: ReAgVisibility = ReAgVisibility.NONE,

    val dealSide: ReAgDealSide = ReAgDealSide.NONE,
    val rentType: ReAgRentType = ReAgRentType.NONE,
    val realtyProperty: ReAgRealtyProperty = ReAgRealtyProperty.NONE,
    var realty: IReAgAdRealty = IReAgAdRealty.NONE,
    val permissionsClient: MutableSet<ReAgAdPermissionClient> = mutableSetOf()
)
package ru.ibikmetov.kotlin.realestateagency.common.models

import ru.ibikmetov.kotlin.realestateagency.common.models.realty.IReAgAdRealty

data class ReAgAd(
    var id: ReAgAdId = ReAgAdId.NONE,
    var title: String = "",
    var description: String = "",
    var address: String = "",
    var ownerId: ReAgUserId = ReAgUserId.NONE,
    var visibility: ReAgVisibility = ReAgVisibility.NONE,

    var dealSide: ReAgDealSide = ReAgDealSide.NONE,
    val rentType: ReAgRentType = ReAgRentType.NONE,
    val realtyProperty: ReAgRealtyProperty = ReAgRealtyProperty.NONE,
    var realty: IReAgAdRealty = IReAgAdRealty.NONE,
    val permissionsClient: MutableSet<ReAgAdPermissionClient> = mutableSetOf()
) {
    fun deepCopy(
    ) = ReAgAd(
        id = this@ReAgAd.id,
        title = this@ReAgAd.title,
        description = this@ReAgAd.description,
        address = this@ReAgAd.address,
        ownerId = this@ReAgAd.ownerId,
        visibility = this@ReAgAd.visibility,
        dealSide = this@ReAgAd.dealSide,
        rentType = this@ReAgAd.rentType,
        realtyProperty = this@ReAgAd.realtyProperty,
        realty = this@ReAgAd.realty.deepCopy(),
        permissionsClient = this@ReAgAd.permissionsClient.toMutableSet()
    )
}
package ru.ibikmetov.kotlin.realestateagency.inmemory.entityes

import ru.ibikmetov.kotlin.realestateagency.common.models.*

data class AdEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val address: String? = null,
    val ownerId: String? = null,
    val visibility: String? = null,
    val dealSide: String? = null,
    val rentType: String? = null,
    val realtyProperty: String? = null,
    val realty: IReAgAdRealtyEntity? = null,
    val lock: String? = null,
) {
    constructor(model: ReAgAd): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        address = model.address.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        visibility = model.visibility.takeIf { it != ReAgVisibility.NONE }?.name,
        dealSide = model.dealSide.takeIf { it != ReAgDealSide.NONE }?.name,
        rentType = model.rentType.takeIf { it != ReAgRentType.NONE }?.name,
        realty = model.realty.toEntity(),
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = ReAgAd(
        id = id?.let { ReAgAdId(it) }?: ReAgAdId.NONE,
        title = title?: "",
        description = description?: "",
        address = address?: "",
        ownerId = ownerId?.let { ReAgUserId(it) }?: ReAgUserId.NONE,
        visibility = visibility?.let { ReAgVisibility.valueOf(it) }?: ReAgVisibility.NONE,
        dealSide = dealSide?.let { ReAgDealSide.valueOf(it) }?: ReAgDealSide.NONE,
        rentType = rentType?.let { ReAgRentType.valueOf(it) }?: ReAgRentType.NONE,
        realty = realty.toInternal(),
        lock = lock?.let { ReAgAdLock(it) } ?: ReAgAdLock.NONE,
    )
}
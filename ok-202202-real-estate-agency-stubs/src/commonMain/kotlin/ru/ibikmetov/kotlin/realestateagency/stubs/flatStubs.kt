package ru.ibikmetov.kotlin.realestateagency.stubs

import ru.ibikmetov.kotlin.realestateagency.common.models.*

object Flat {
    private fun stubReady() = ReAgAd(
        id = ReAgAdId(id = "1"),
        title = "Неплохая квартира",
        description = "Неплохая квартира с видом на кладбище. Тихие соседи",
        ownerId = ReAgUserId(id = "2000"),
        visibility = ReAgVisibility.VISIBLE_PUBLIC,
        dealSide = ReAgDealSide.DEMAND,
        rentType = ReAgRentType.DAILY,
        permissionsClient = mutableSetOf(ReAgAdPermissionClient.READ)
    )

    private fun stubInProgress() = ReAgAd(
        id = ReAgAdId(id = "2"),
        title = "Совсем плохая квартира",
        description = "Совсем плохая квартира, но для студента сойдёт",
        ownerId = ReAgUserId(id = "2001"),
        visibility = ReAgVisibility.VISIBLE_TO_OWNER,
        dealSide = ReAgDealSide.DEMAND,
        rentType = ReAgRentType.DAILY,
        permissionsClient = mutableSetOf(ReAgAdPermissionClient.MAKE_VISIBLE_OWNER)
    )

    fun getModel(model: (ReAgAd.() -> Unit)? = null) = model?.let {
        stubReady().apply(it)
    } ?: stubReady()

    fun getModels() = listOf(
        stubReady(),
        stubInProgress()
    )

    fun ReAgAd.update(updateableAd: ReAgAd) = apply {
        title = updateableAd.title
        description = updateableAd.description
        ownerId = updateableAd.ownerId
        visibility = updateableAd.visibility
        permissionsClient.addAll(updateableAd.permissionsClient)
    }
}
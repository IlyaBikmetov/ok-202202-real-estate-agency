package ru.ibikmetov.kotlin.realestateagency.stubs

import ru.ibikmetov.kotlin.realestateagency.common.models.*
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyFlat
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyStoveType

object ReAgAdStubFlats {
    val AD_DEMAND_FLAT: ReAgAd
        get() = ReAgAd(
            id = ReAgAdId("1"),
            title = "Требуется квартира",
            description = "Требуется небольшая квартира для студента, желательно недалеко от университета",
            address = "Ленина",
            ownerId = ReAgUserId("user-1"),
            visibility = ReAgVisibility.VISIBLE_PUBLIC,
            dealSide = ReAgDealSide.DEMAND,
            rentType = ReAgRentType.LONG,
            realtyProperty = ReAgRealtyProperty.FLAT,
            realty = ReAgAdRealtyFlat(
                floor = 7,
                square = 45.6F,
                cntRooms = 1,
                stoveType = ReAgAdRealtyStoveType.GAS,
                balcony = true,
            ),
            permissionsClient = mutableSetOf(
                ReAgAdPermissionClient.READ,
                ReAgAdPermissionClient.UPDATE,
                ReAgAdPermissionClient.DELETE,
                ReAgAdPermissionClient.MAKE_VISIBLE_PUBLIC,
                ReAgAdPermissionClient.MAKE_VISIBLE_GROUP,
                ReAgAdPermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
    val AD_PROPOSAL_FLAT = AD_DEMAND_FLAT.copy(
        dealSide = ReAgDealSide.PROPOSAL,
    )
}
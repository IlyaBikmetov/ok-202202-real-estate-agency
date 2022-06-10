package ru.ibikmetov.kotlin.realestateagency.spring.service

import org.springframework.stereotype.Service
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.*
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.*

@Service
class AdService {
    fun createAd(context: ReAgContext): ReAgContext {
        return context.apply {
            context.adResponse = ReAgAd(id = ReAgAdId("999"))
        }
    }

    fun readAd(context: ReAgContext): ReAgContext {
        return context.apply {
            context.adResponse = ReAgAd (
                id = context.adRequest.id,
                title = "Title2",
                description = "Description2",
                address = "Address2",
                dealSide = ReAgDealSide.DEMAND,
                rentType = ReAgRentType.DAILY,
                realtyProperty = ReAgRealtyProperty.OFFICE,
                visibility = ReAgVisibility.VISIBLE_TO_OWNER
            )
        }
    }

    fun updateAd(context: ReAgContext): ReAgContext {
        return context.apply {
            context.adResponse = ReAgAd (
                id = context.adRequest.id,
                title = context.adRequest.title,
                description = context.adRequest.description,
                address = context.adRequest.address,
                dealSide = context.adRequest.dealSide,
                rentType = context.adRequest.rentType,
                realtyProperty = context.adRequest.realtyProperty,
                visibility = context.adRequest.visibility,
            )
        }
    }

    fun deleteAd(context: ReAgContext): ReAgContext {
        context.adRequest
        return context.apply {
            context.adRequest = adRequest
        }
    }

    fun searchAd(context: ReAgContext): ReAgContext {
        context.adRequest
        return context.apply {
            context.adRequest = adRequest
        }
    }
}

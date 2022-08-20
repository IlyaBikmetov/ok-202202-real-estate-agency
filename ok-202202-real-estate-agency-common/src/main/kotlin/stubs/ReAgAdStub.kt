package ru.ibikmetov.kotlin.realestateagency.common.stubs

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAd
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAdId
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgDealSide
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgAdStubFlats.AD_DEMAND_FLAT
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgAdStubFlats.AD_PROPOSAL_FLAT

object ReAgAdStub {
    fun get(): ReAgAd = AD_DEMAND_FLAT.copy()

    fun prepareResult(block: ReAgAd.() -> Unit): ReAgAd = get().apply(block)

    fun prepareSearchList(filter: String, type: ReAgDealSide) = listOf(
        ReAgAdDemand("1", filter, type),
        ReAgAdDemand("2", filter, type),
        ReAgAdDemand("3", filter, type),
        ReAgAdDemand("4", filter, type),
        ReAgAdDemand("5", filter, type),
        ReAgAdDemand("6", filter, type),
    )

    private fun ReAgAdDemand(id: String, filter: String, dealSide: ReAgDealSide) =
        ReAgAd(AD_DEMAND_FLAT, id = id, filter = filter, dealSide = dealSide)

    private fun ReAgAdProposal(id: String, filter: String, dealSide: ReAgDealSide) =
        ReAgAd(AD_PROPOSAL_FLAT, id = id, filter = filter, dealSide = dealSide)

    private fun ReAgAd(base: ReAgAd, id: String, filter: String, dealSide: ReAgDealSide) = base.copy(
        id = ReAgAdId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        dealSide = dealSide,
    )

}
package ru.ibikmetov.kotlin.realestateagency.common.repository

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAd
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgError

data class DbAdsResponse(
    override val result: List<ReAgAd>?,
    override val isSuccess: Boolean,
    override val errors: List<ReAgError> = emptyList(),
): IDbResponse<List<ReAgAd>> {

    companion object {
        fun success(result: List<ReAgAd>) = DbAdsResponse(result, true)
        fun error(errors: List<ReAgError>) = DbAdsResponse(null, false, errors)
        fun error(error: ReAgError) = DbAdsResponse(null, false, listOf(error))
    }
}
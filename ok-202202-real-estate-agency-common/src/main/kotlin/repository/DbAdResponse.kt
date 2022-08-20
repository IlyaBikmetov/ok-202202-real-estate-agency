package ru.ibikmetov.kotlin.realestateagency.common.repository

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAd
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgError

data class DbAdResponse(
    override val result: ReAgAd?,
    override val isSuccess: Boolean,
    override val errors: List<ReAgError> = emptyList()
): IDbResponse<ReAgAd> {

    companion object {
        fun success(result: ReAgAd) = DbAdResponse(result, true)
        fun error(errors: List<ReAgError>) = DbAdResponse(null, false, errors)
        fun error(error: ReAgError) = DbAdResponse(null, false, listOf(error))
    }
}
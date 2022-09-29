package ru.ibikmetov.kotlin.realestateagency.business.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAd
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdFilterRequest
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdRequest

fun ICorChainDsl<ReAgContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД"
    on { state == ReAgState.RUNNING }
    handle {
        val request = DbAdFilterRequest(adFilterValidating.searchString)
        val result = adRepo.searchAd(request)
        val resultAds = result.result
        if (result.isSuccess) {
            adsRepoDone = resultAds as MutableList<ReAgAd>
        } else {
            state = ReAgState.FAILING
            errors.addAll(result.errors)
        }
    }
}
package ru.ibikmetov.kotlin.realestateagency.business.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAd
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdIdRequest

fun ICorChainDsl<ReAgContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД"
    on { state == ReAgState.RUNNING }
    handle {
        val request = DbAdIdRequest(adRepoPrepare)
        val result = adRepo.deleteAd(request)
        if (result.isSuccess) {
            adRepoDone = ReAgAd()
        } else {
            state = ReAgState.FAILING
            errors.addAll(result.errors)
        }
    }
}
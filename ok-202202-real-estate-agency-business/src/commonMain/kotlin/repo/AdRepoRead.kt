package ru.ibikmetov.kotlin.realestateagency.business.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdIdRequest

fun ICorChainDsl<ReAgContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == ReAgState.RUNNING }
    handle {
        val request = DbAdIdRequest(adRepoPrepare)
        val result = adRepo.readAd(request)
        val resultAd = result.result
        if (result.isSuccess && resultAd != null) {
            adRepoDone = resultAd
        } else {
            state = ReAgState.FAILING
            errors.addAll(result.errors)
        }
    }
}
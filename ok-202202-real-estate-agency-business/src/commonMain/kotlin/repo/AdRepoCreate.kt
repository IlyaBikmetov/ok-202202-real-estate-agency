package ru.ibikmetov.kotlin.realestateagency.business.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdRequest

fun ICorChainDsl<ReAgContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление объявления в БД"
    on { state == ReAgState.RUNNING }
    handle {
        val request = DbAdRequest(adRepoPrepare)
        println("repoCreate request: $request")
        val result = adRepo.createAd(request)
        println("repoCreate result: $result")
        val resultAd = result.result
        if (result.isSuccess && resultAd != null) {
            adRepoDone = resultAd
        } else {
            state = ReAgState.FAILING
            errors.addAll(result.errors)
        }
    }
}
package ru.ibikmetov.kotlin.realestateagency.business.general

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext

fun ICorChainDsl<ReAgContext>.initRepo(title: String) = worker {
    this.title = title
    description = "Вычисление основного рабочего репозитория"
    handle {
        adRepo = settings.repo
    }
}
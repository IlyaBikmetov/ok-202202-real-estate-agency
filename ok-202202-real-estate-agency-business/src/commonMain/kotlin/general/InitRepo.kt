package ru.ibikmetov.kotlin.realestateagency.business.general

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.cassandra.InitCassandra
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.repository.IAdRepository

fun ICorChainDsl<ReAgContext>.initRepo(title: String) = worker {
    this.title = title
    description = "Вычисление основного рабочего репозитория"
    handle {
        if (settings.repo == IAdRepository.NONE) {
            adRepo = InitCassandra.repository()
        } else {
            adRepo = settings.repo
        }
    }
}
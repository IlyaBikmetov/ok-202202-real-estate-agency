package ru.ibikmetov.kotlin.realestateagency.business.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState

fun ICorChainDsl<ReAgContext>.repoPrepareRead(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к чтению из БД"
    on { state == ReAgState.RUNNING }
    handle {
        adRepoPrepare = adValidated.deepCopy()
    }
}
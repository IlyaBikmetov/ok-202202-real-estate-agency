package ru.ibikmetov.kotlin.realestateagency.business.general

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState

fun ICorChainDsl<ReAgContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == ReAgState.NONE }
    handle { state = ReAgState.RUNNING }
}
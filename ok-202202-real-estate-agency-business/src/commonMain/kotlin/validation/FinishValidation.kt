package ru.ibikmetov.kotlin.realestateagency.business.validation

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState

fun ICorChainDsl<ReAgContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == ReAgState.RUNNING }
    handle {
        adValidated = adValidating
    }
}

fun ICorChainDsl<ReAgContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == ReAgState.RUNNING }
    handle {
        adFilterValidated = adFilterValidating
    }
}
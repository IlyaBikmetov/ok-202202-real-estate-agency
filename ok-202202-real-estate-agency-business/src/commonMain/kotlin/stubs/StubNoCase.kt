package ru.ibikmetov.kotlin.realestateagency.business.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgError
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState

fun ICorChainDsl<ReAgContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == ReAgState.RUNNING }
    handle {
        state = ReAgState.FAILING
        this.errors.add(
            ReAgError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
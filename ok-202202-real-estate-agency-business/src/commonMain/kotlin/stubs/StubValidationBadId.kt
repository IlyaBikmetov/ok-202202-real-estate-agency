package ru.ibikmetov.kotlin.realestateagency.business.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgError
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgStubs

fun ICorChainDsl<ReAgContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { stubCase == ReAgStubs.BAD_ID && state == ReAgState.RUNNING }
    handle {
        state = ReAgState.FAILING
        this.errors.add(
            ReAgError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}
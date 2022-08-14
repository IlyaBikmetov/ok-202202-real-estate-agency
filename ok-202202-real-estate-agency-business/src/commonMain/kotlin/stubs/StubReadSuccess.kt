package ru.ibikmetov.kotlin.realestateagency.business.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgStubs
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgAdStub

fun ICorChainDsl<ReAgContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == ReAgStubs.SUCCESS && state == ReAgState.RUNNING }
    handle {
        state = ReAgState.FINISHING
        val stub = ReAgAdStub.prepareResult {
            adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
        }
        adResponse = stub
    }
}
package ru.ibikmetov.kotlin.realestateagency.business.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgDealSide
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgVisibility
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgStubs
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgAdStub

fun ICorChainDsl<ReAgContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == ReAgStubs.SUCCESS && state == ReAgState.RUNNING }
    handle {
        state = ReAgState.FINISHING
        val stub = ReAgAdStub.prepareResult {
            adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            adRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            adRequest.dealSide.takeIf { it != ReAgDealSide.NONE }?.also { this.dealSide = it }
            adRequest.visibility.takeIf { it != ReAgVisibility.NONE }?.also { this.visibility = it }
        }
        adResponse = stub
    }
}
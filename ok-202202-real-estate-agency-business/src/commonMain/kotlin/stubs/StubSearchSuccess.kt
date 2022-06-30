package ru.ibikmetov.kotlin.realestateagency.business.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.*
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgStubs
import ru.ibikmetov.kotlin.realestateagency.stubs.ReAgAdStub

fun ICorChainDsl<ReAgContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stubCase == ReAgStubs.SUCCESS && state == ReAgState.RUNNING }
    handle {
        state = ReAgState.FINISHING
        adsResponse.addAll(ReAgAdStub.prepareSearchList(adFilterRequest.searchString, ReAgDealSide.DEMAND))
    }
}
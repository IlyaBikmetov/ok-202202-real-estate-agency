package ru.ibikmetov.kotlin.realestateagency.business.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgWorkMode

fun ICorChainDsl<ReAgContext>.stubs(title: String, block: ICorChainDsl<ReAgContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == ReAgWorkMode.STUB && state == ReAgState.RUNNING }
}
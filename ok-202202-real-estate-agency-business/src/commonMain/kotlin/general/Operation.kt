package ru.ibikmetov.kotlin.realestateagency.business.general

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgCommand
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState

fun ICorChainDsl<ReAgContext>.operation(title: String, command: ReAgCommand, block: ICorChainDsl<ReAgContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == ReAgState.RUNNING }
}
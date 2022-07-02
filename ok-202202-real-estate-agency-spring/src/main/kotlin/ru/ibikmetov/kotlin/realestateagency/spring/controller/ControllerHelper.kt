package ru.ibikmetov.kotlin.realestateagency.spring.controller

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.IRequest
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.IResponse
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.helpers.asReAgError
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgCommand
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState
import ru.ibikmetov.kotlin.realestateagency.mappers.fromTransport
import ru.ibikmetov.kotlin.realestateagency.mappers.toTransportAd

inline fun <reified Q : IRequest, reified R : IResponse> controllerHelper(
    request: Q,
    command: ReAgCommand? = null,
    noinline block: suspend ReAgContext.() -> Unit,
): R = runBlocking {
    val ctx = ReAgContext(
        timeStart = Clock.System.now(),
    )
    try {
        ctx.fromTransport(request)
        ctx.block()
        ctx.toTransportAd() as R
    } catch (e: Throwable) {
        command?.also { ctx.command = it }
        ctx.state = ReAgState.FAILING
        ctx.errors.add(e.asReAgError())
        ctx.block()
        ctx.toTransportAd() as R
    }
}
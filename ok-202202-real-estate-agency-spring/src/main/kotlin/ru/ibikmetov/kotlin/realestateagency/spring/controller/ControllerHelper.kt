package ru.ibikmetov.kotlin.realestateagency.spring.controller

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import org.springframework.security.core.context.SecurityContextHolder
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.IRequest
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.IResponse
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.helpers.asReAgError
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgCommand
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgPrincipalModel
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState
import ru.ibikmetov.kotlin.realestateagency.logging.ReAgLogWrapper
import ru.ibikmetov.kotlin.realestateagency.logs.toLog
import ru.ibikmetov.kotlin.realestateagency.mappers.fromTransport
import ru.ibikmetov.kotlin.realestateagency.mappers.toTransportAd

inline fun <reified Q : IRequest, reified R : IResponse> controllerHelper(
    request: Q,
    logger: ReAgLogWrapper,
    logId: String,
    command: ReAgCommand? = null,
    noinline block: suspend ReAgContext.() -> Unit,
): R = runBlocking {
    val ctx = ReAgContext(
        timeStart = Clock.System.now(),
        principal = SecurityContextHolder
            .getContext()
            .authentication
            .principal as ReAgPrincipalModel
    )
    try {
        logger.doWithLogging {
            ctx.fromTransport(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            ctx.block()
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            ctx.toTransportAd() as R
        }
    } catch (e: Throwable) {
        command?.also { ctx.command = it }
        ctx.state = ReAgState.FAILING
        ctx.errors.add(e.asReAgError())
        ctx.block()
        ctx.toTransportAd() as R
    }
}
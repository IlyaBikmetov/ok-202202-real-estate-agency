package ru.ibikmetov.kotlin.realestateagency.mappers

import ru.ibikmetov.kotlin.realestateagency.common.*
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.*
import ru.ibikmetov.kotlin.realestateagency.common.models.*
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgStubs
import ru.ibikmetov.kotlin.realestateagency.mappers.exceptions.UnknownRequestClass

fun ReAgContext.fromTransport(request: IRequest) = when(request){
    is AdCreateRequest -> fromTransport(request)
    is AdReadRequest   -> fromTransport(request)
    is AdUpdateRequest -> fromTransport(request)
    is AdDeleteRequest -> fromTransport(request)
    is AdSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toAdId() = this?.let { ReAgAdId(it) } ?: ReAgAdId.NONE
private fun BaseAdIdRequestAd?.toAdWithId() = ReAgAd(id = this?.id.toAdId())
private fun IRequest?.requestId() = this?.requestId?.let { ReAgRequestId(it) } ?: ReAgRequestId.NONE

private fun AdDebug?.transportToWorkMode(): ReAgWorkMode = when(this?.mode) {
    AdRequestDebugMode.PROD -> ReAgWorkMode.PROD
    AdRequestDebugMode.TEST -> ReAgWorkMode.TEST
    AdRequestDebugMode.STUB -> ReAgWorkMode.STUB
    null -> ReAgWorkMode.PROD
}

private fun AdDebug?.transportToStubCase(): ReAgStubs = when(this?.stub) {
    AdRequestDebugStubs.SUCCESS -> ReAgStubs.SUCCESS
    AdRequestDebugStubs.NOT_FOUND -> ReAgStubs.NOT_FOUND
    AdRequestDebugStubs.BAD_ID -> ReAgStubs.BAD_ID
    AdRequestDebugStubs.BAD_TITLE -> ReAgStubs.BAD_TITLE
    AdRequestDebugStubs.BAD_DESCRIPTION -> ReAgStubs.BAD_DESCRIPTION
    AdRequestDebugStubs.BAD_VISIBILITY -> ReAgStubs.BAD_VISIBILITY
    AdRequestDebugStubs.CANNOT_DELETE -> ReAgStubs.CANNOT_DELETE
    AdRequestDebugStubs.BAD_SEARCH_STRING -> ReAgStubs.BAD_SEARCH_STRING
    null -> ReAgStubs.NONE
}

fun ReAgContext.fromTransport(request: AdCreateRequest) {
    command = ReAgCommand.CREATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: ReAgAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ReAgContext.fromTransport(request: AdReadRequest) {
    command = ReAgCommand.READ
    requestId = request.requestId()
    adRequest = request.ad.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ReAgContext.fromTransport(request: AdUpdateRequest) {
    command = ReAgCommand.UPDATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: ReAgAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ReAgContext.fromTransport(request: AdDeleteRequest) {
    command = ReAgCommand.DELETE
    requestId = request.requestId()
    adRequest = request.ad.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ReAgContext.fromTransport(request: AdSearchRequest) {
    command = ReAgCommand.SEARCH
    requestId = request.requestId()
    adFilterRequest = request.adFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun AdSearchFilter?.toInternal(): ReAgAdFilter = ReAgAdFilter(
    searchString = this?.searchString ?: ""
)

private fun AdCreateObject.toInternal(): ReAgAd = ReAgAd(
    title = this.title ?: "",
    description = this.description ?: "",
    address = this.address ?: "",
    visibility = this.visibility.fromTransport(),
    dealSide = this.dealside.fromTransport(),
    rentType = this.renttype.fromTransport(),
)

private fun AdUpdateObject.toInternal(): ReAgAd = ReAgAd(
    id = this.id.toAdId(),
    title = this.title ?: "",
    description = this.description ?: "",
    address = this.address ?: "",
    visibility = this.visibility.fromTransport(),
    dealSide = this.dealside.fromTransport(),
    rentType = this.renttype.fromTransport(),
)

private fun AdVisibility?.fromTransport(): ReAgVisibility = when(this) {
    AdVisibility.PUBLIC -> ReAgVisibility.VISIBLE_PUBLIC
    AdVisibility.OWNER_ONLY -> ReAgVisibility.VISIBLE_TO_OWNER
    AdVisibility.REGISTERED_ONLY -> ReAgVisibility.VISIBLE_TO_GROUP
    null -> ReAgVisibility.NONE
}

private fun DealSide?.fromTransport(): ReAgDealSide = when(this) {
    DealSide.DEMAND -> ReAgDealSide.DEMAND
    DealSide.PROPOSAL -> ReAgDealSide.SUPPLY
    null -> ReAgDealSide.NONE
}

private fun RentType?.fromTransport(): ReAgRentType = when(this) {
    RentType.LONG -> ReAgRentType.LONG
    RentType.DAILY -> ReAgRentType.DAILY
    null -> ReAgRentType.NONE
}
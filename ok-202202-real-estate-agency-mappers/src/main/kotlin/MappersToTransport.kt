package ru.ibikmetov.kotlin.realestateagency.mappers

import ru.ibikmetov.kotlin.realestateagency.api.v1.models.*
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.*
import ru.ibikmetov.kotlin.realestateagency.mappers.exceptions.UnknownReAgCommand

fun ReAgContext.toTransportAd(): IResponse = when (val cmd = command) {
    ReAgCommand.CREATE -> toTransportCreate()
    ReAgCommand.READ -> toTransportRead()
    ReAgCommand.UPDATE -> toTransportRead()
    ReAgCommand.DELETE -> toTransportRead()
    ReAgCommand.SEARCH -> toTransportRead()
    ReAgCommand.OFFERS -> toTransportRead()
    ReAgCommand.NONE -> throw UnknownReAgCommand(cmd)
}

fun ReAgContext.toTransportCreate() = AdCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ReAgState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun ReAgContext.toTransportRead() = AdReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ReAgState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun ReAgContext.toTransportUpdate() = AdUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ReAgState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun ReAgContext.toTransportDelete() = AdDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ReAgState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun ReAgContext.toTransportSearch() = AdSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ReAgState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransportAd()
)

fun List<ReAgAd>.toTransportAd(): List<AdResponseObject>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ReAgAd.toTransportAd(): AdResponseObject = AdResponseObject(
    id = id.takeIf { it != ReAgAdId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != ReAgUserId.NONE }?.asString(),
    visibility = visibility.toTransportAd(),
    dealside = this.dealSide.toTransportAd(),
    renttype = this.rentType.toTransportAd(),
    permissions = permissionsClient.toTransportAd(),
)

private fun Set<ReAgAdPermissionClient>.toTransportAd(): Set<AdPermissions>? = this
    .map { it.toTransportAd() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun ReAgAdPermissionClient.toTransportAd() = when (this) {
    ReAgAdPermissionClient.READ -> AdPermissions.READ
    ReAgAdPermissionClient.UPDATE -> AdPermissions.UPDATE
    ReAgAdPermissionClient.MAKE_VISIBLE_OWNER -> AdPermissions.MAKE_VISIBLE_OWN
    ReAgAdPermissionClient.MAKE_VISIBLE_GROUP -> AdPermissions.MAKE_VISIBLE_GROUP
    ReAgAdPermissionClient.MAKE_VISIBLE_PUBLIC -> AdPermissions.MAKE_VISIBLE_PUBLIC
    ReAgAdPermissionClient.DELETE -> AdPermissions.DELETE
}

private fun ReAgVisibility.toTransportAd(): AdVisibility? = when (this) {
    ReAgVisibility.VISIBLE_PUBLIC -> AdVisibility.PUBLIC
    ReAgVisibility.VISIBLE_TO_GROUP -> AdVisibility.REGISTERED_ONLY
    ReAgVisibility.VISIBLE_TO_OWNER -> AdVisibility.OWNER_ONLY
    ReAgVisibility.NONE -> null
}

private fun ReAgDealSide.toTransportAd(): DealSide? = when (this) {
    ReAgDealSide.DEMAND -> DealSide.DEMAND
    ReAgDealSide.SUPPLY -> DealSide.PROPOSAL
    ReAgDealSide.NONE -> null
}

private fun ReAgRentType.toTransportAd(): RentType? = when (this) {
    ReAgRentType.LONG -> RentType.LONG
    ReAgRentType.DAILY -> RentType.DAILY
    ReAgRentType.NONE -> null
}

private fun List<ReAgError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ReAgError.toTransportAd() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
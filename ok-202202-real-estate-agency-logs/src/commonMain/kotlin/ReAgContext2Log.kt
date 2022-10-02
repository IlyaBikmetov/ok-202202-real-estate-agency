package ru.ibikmetov.kotlin.realestateagency.logs

import com.benasher44.uuid.uuid4
import kotlinx.datetime.Clock
import ru.ibikmetov.kotlin.realestateagency.api.logs.models.*
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.*

fun ReAgContext.toLog(logId: String) = CommonLogModel(
    messageId = uuid4().toString(),
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "ok-real-estate-agency",
    marketplace = toReAgLog(),
    errors = errors.map { it.toLog() },
)

fun ReAgContext.toReAgLog():ReAgLogModel? {
    val adNone = ReAgAd()
    return ReAgLogModel(
        requestId = requestId.takeIf { it != ReAgRequestId.NONE }?.asString(),
        requestAd = adRequest.takeIf { it != adNone }?.toLog(),
        responseAd = adResponse.takeIf { it != adNone }?.toLog(),
        responseAds = adsResponse.takeIf { it.isNotEmpty() }?.filter { it != adNone }?.map { it.toLog() },
        requestFilter = adFilterRequest.takeIf { it != ReAgAdFilter() }?.toLog(),
    ).takeIf { it != ReAgLogModel() }
}

private fun ReAgAdFilter.toLog() = AdFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != ReAgUserId.NONE }?.asString(),
    dealSide = dealSide.takeIf { it != ReAgDealSide.NONE }?.name,
    searchTypes = searchTypes.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)

fun ReAgError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun ReAgAd.toLog() = AdLog(
    id = id.takeIf { it != ReAgAdId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    dealSide = dealSide.takeIf { it != ReAgDealSide.NONE }?.name,
    rentType  = rentType.takeIf { it != ReAgRentType.NONE }?.name,
    visibility = visibility.takeIf { it != ReAgVisibility.NONE }?.name,
    ownerId = ownerId.takeIf { it != ReAgUserId.NONE }?.asString(),
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)
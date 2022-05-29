package ru.ibikmetov.kotlin.realestateagency.common

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.ibikmetov.kotlin.realestateagency.common.models.*
import ru.ibikmetov.kotlin.realestateagency.common.stubs.*

data class ReAgContext(
    var command: ReAgCommand = ReAgCommand.NONE,
    var state: ReAgState = ReAgState.NONE,

    val errors: MutableList<ReAgError> = mutableListOf(),

    var workMode: ReAgWorkMode = ReAgWorkMode.PROD,
    var stubCase: ReAgStubs = ReAgStubs.NONE,

    var requestId: ReAgRequestId = ReAgRequestId.NONE,
    var timeStart: Instant = Clock.System.now(),
    var adRequest: ReAgAd = ReAgAd(),
    var adFilterRequest: ReAgAdFilter = ReAgAdFilter(),
    var adResponse: ReAgAd = ReAgAd(),
    var adsResponse: MutableList<ReAgAd> = mutableListOf()
)
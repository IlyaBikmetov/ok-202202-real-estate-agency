package ru.ibikmetov.kotlin.realestateagency.common

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.ibikmetov.kotlin.realestateagency.common.models.*
import ru.ibikmetov.kotlin.realestateagency.common.repository.IAdRepository
import ru.ibikmetov.kotlin.realestateagency.common.stubs.*

data class ReAgContext(
    var settings: ReAgSettings = ReAgSettings(),
    var command: ReAgCommand = ReAgCommand.NONE,
    var state: ReAgState = ReAgState.NONE,

    val errors: MutableList<ReAgError> = mutableListOf(),

    var workMode: ReAgWorkMode = ReAgWorkMode.PROD,
    var stubCase: ReAgStubs = ReAgStubs.NONE,

    var clientSession: IClientSession<*> = IClientSession.NONE,
    var principal: ReAgPrincipalModel = ReAgPrincipalModel.NONE,
    val chainPermissions: MutableSet<ReAgUserPermissions> = mutableSetOf(),
    var permitted: Boolean = false,

    var adRepo: IAdRepository = IAdRepository.NONE,

    var requestId: ReAgRequestId = ReAgRequestId.NONE,
    var timeStart: Instant = Clock.System.now(),
    var adRequest: ReAgAd = ReAgAd(),
    var adFilterRequest: ReAgAdFilter = ReAgAdFilter(),

    var adValidating: ReAgAd = ReAgAd(),
    var adFilterValidating: ReAgAdFilter = ReAgAdFilter(),

    var adValidated: ReAgAd = ReAgAd(),
    var adFilterValidated: ReAgAdFilter = ReAgAdFilter(),

    var adRepoRead: ReAgAd = ReAgAd(),
    var adRepoPrepare: ReAgAd = ReAgAd(),
    var adRepoDone: ReAgAd = ReAgAd(),
    var adsRepoDone: MutableList<ReAgAd> = mutableListOf(),

    var adResponse: ReAgAd = ReAgAd(),
    var adsResponse: MutableList<ReAgAd> = mutableListOf()
)
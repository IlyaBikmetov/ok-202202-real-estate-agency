package ru.ibikmetov.kotlin.realestateagency.spring.controller

import org.springframework.web.bind.annotation.*

import ru.ibikmetov.kotlin.realestateagency.api.v1.models.*
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.mappers.*
import ru.ibikmetov.kotlin.realestateagency.spring.service.AdService

@RequestMapping("ad")
@RestController
class AdController(private val adService: AdService) {

    @PostMapping("create")
    fun adCreate(@RequestBody body: AdCreateRequest): AdCreateResponse {
        val context = ReAgContext()
        context.fromTransport(body)
        return adService.createAd(context).toTransportCreate()
    }

    @PostMapping("read")
    fun adRead(@RequestBody body: AdReadRequest): AdReadResponse {
        val context = ReAgContext()
        context.fromTransport(body)
        return adService.readAd(context).toTransportRead()
    }

    @PostMapping("update")
    fun adRead(@RequestBody body: AdUpdateRequest): AdUpdateResponse {
        val context = ReAgContext()
        context.fromTransport(body)
        return adService.updateAd(context).toTransportUpdate()
    }

    @PostMapping("delete")
    fun adDelete(@RequestBody body: AdDeleteRequest): AdDeleteResponse {
        val context = ReAgContext()
        context.fromTransport(body)
        return adService.deleteAd(context).toTransportDelete()
    }

    @PostMapping("search")
    fun adSearch(@RequestBody body: AdSearchRequest): AdSearchResponse {
        val context = ReAgContext()
        context.fromTransport(body)
        return adService.searchAd(context).toTransportSearch()
    }
}
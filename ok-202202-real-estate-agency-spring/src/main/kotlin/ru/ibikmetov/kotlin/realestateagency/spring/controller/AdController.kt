package ru.ibikmetov.kotlin.realestateagency.spring.controller

import org.springframework.web.bind.annotation.*

import ru.ibikmetov.kotlin.realestateagency.api.v1.models.*
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgCommand
import ru.ibikmetov.kotlin.realestateagency.spring.service.AdService

@RequestMapping("ad")
@RestController
class AdController(private val adService: AdService) {

    @PostMapping("create")
    fun adCreate(@RequestBody request: AdCreateRequest): AdCreateResponse =
        controllerHelper(request, ReAgCommand.CREATE) {
            adService.createAd(this)
        }

    @PostMapping("read")
    fun adRead(@RequestBody request: AdReadRequest): AdReadResponse =
        controllerHelper(request, ReAgCommand.READ) {
            adService.readAd(this)
        }

    @RequestMapping("update", method = [RequestMethod.POST])
    fun adUpdate(@RequestBody request: AdUpdateRequest): AdUpdateResponse =
        controllerHelper(request, ReAgCommand.UPDATE) {
            adService.updateAd(this)
        }

    @PostMapping("delete")
    fun adDelete(@RequestBody request: AdDeleteRequest): AdDeleteResponse =
        controllerHelper(request, ReAgCommand.DELETE) {
            adService.deleteAd(this)
        }

    @PostMapping("search")
    fun adSearch(@RequestBody request: AdSearchRequest): AdSearchResponse =
        controllerHelper(request, ReAgCommand.SEARCH) {
            adService.searchAd(this)
        }
}
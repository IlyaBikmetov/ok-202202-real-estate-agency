package ru.ibikmetov.kotlin.realestateagency.mappers

import org.junit.Test
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.*
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.*
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgStubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = AdCreateRequest(
            requestId = "1234",
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS,
            ),
            ad = AdCreateObject(
                title = "title",
                description = "desc",
                address = "address",
                dealside = DealSide.DEMAND,
                renttype = RentType.DAILY,
                //realtyproperty = RealtyProperty.STORAGE,
                visibility = AdVisibility.PUBLIC,
            ),
        )

        val context = ReAgContext()
        context.fromTransport(req)

        assertEquals("1234", context.requestId.asString())
        assertEquals(ReAgWorkMode.STUB, context.workMode)
        assertEquals(ReAgStubs.SUCCESS, context.stubCase)
        assertEquals("title", context.adRequest.title)
        assertEquals("desc", context.adRequest.description)
        assertEquals("address", context.adRequest.address)
        assertEquals(ReAgDealSide.DEMAND, context.adRequest.dealSide)
        assertEquals(ReAgRentType.DAILY, context.adRequest.rentType)
        assertEquals(ReAgRealtyProperty.STORAGE, context.adRequest.realtyProperty)
        assertEquals(ReAgVisibility.VISIBLE_PUBLIC, context.adRequest.visibility)
    }

    @Test
    fun toTransport() {
        val context = ReAgContext(
            requestId = ReAgRequestId("1234"),
            command = ReAgCommand.CREATE,
            adResponse = ReAgAd(
                title = "title",
                description = "desc",
                address = "address",
                dealSide = ReAgDealSide.PROPOSAL,
                rentType = ReAgRentType.DAILY,
                realtyProperty = ReAgRealtyProperty.ROOM,
                visibility = ReAgVisibility.VISIBLE_PUBLIC,
            ),
            errors = mutableListOf(
                ReAgError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = ReAgState.RUNNING,
        )

        val req = context.toTransportAd() as AdCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("title", req.ad?.title)
        assertEquals("desc", req.ad?.description)
        assertEquals("address", req.ad?.address)
        assertEquals(DealSide.PROPOSAL, req.ad?.dealside)
        assertEquals(RentType.DAILY, req.ad?.renttype)
        assertEquals(RealtyProperty.ROOM, req.ad?.realty?.realtyproperty)
        assertEquals(AdVisibility.PUBLIC, req.ad?.visibility)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
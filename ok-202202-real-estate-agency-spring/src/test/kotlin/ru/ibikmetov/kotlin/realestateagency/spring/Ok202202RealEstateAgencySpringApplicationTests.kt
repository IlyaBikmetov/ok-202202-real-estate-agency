package ru.ibikmetov.kotlin.realestateagency.spring

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.*
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAdId

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class StubTest {
    val port: Int = 8082

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun create() {
        println("PORT: $port")
        webTestClient
            .post()
            .uri("http://127.0.0.1:$port/ad/create")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(AdCreateRequest(
                requestType = "create",
                debug = AdDebug(mode = AdRequestDebugMode.STUB, stub = AdRequestDebugStubs.SUCCESS)
            ))
            .exchange()
            .expectStatus().isOk
            .expectBody(AdCreateResponse::class.java).consumeWith {
                val dat = it.responseBody
                assertThat(dat?.ad?.id).isEqualTo("999")
            }
    }

    @Test
    fun read() {
        println("PORT: $port")
        webTestClient
            .post()
            .uri("http://127.0.0.1:$port/ad/read")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(
                AdReadRequest(
                    requestType = "read",
                    debug = AdDebug(mode = AdRequestDebugMode.STUB, stub = AdRequestDebugStubs.SUCCESS),
                    ad = BaseAdIdRequestAd(
                        id = "777"
                    )
                )
            )
            .exchange()
            .expectStatus().isOk
            .expectBody(AdReadResponse::class.java).consumeWith {
                val dat = it.responseBody
                assertThat(dat?.ad?.id).isEqualTo("777")
                assertThat(dat?.ad?.title).isEqualTo("Title2")
                assertThat(dat?.ad?.description).isEqualTo("Description2")
                assertThat(dat?.ad?.address).isEqualTo("Address2")
                assertThat(dat?.ad?.dealside).isEqualTo(DealSide.DEMAND)
                assertThat(dat?.ad?.renttype).isEqualTo(RentType.DAILY)
                assertThat(dat?.ad?.realtyproperty).isEqualTo(RealtyProperty.OFFICE)
                assertThat(dat?.ad?.visibility).isEqualTo(AdVisibility.OWNER_ONLY)
            }
    }

    @Test
    fun update() {
        println("PORT: $port")
        webTestClient
            .post()
            .uri("http://127.0.0.1:$port/ad/update")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(AdUpdateRequest(
                requestType = "update",
                debug = AdDebug(mode = AdRequestDebugMode.STUB, stub = AdRequestDebugStubs.SUCCESS),
                ad = AdUpdateObject(
                    title = "Title3",
                    description = "Description3",
                    address = "Address3",
                    dealside = DealSide.PROPOSAL,
                    renttype = RentType.LONG,
                    realtyproperty = RealtyProperty.ROOM,
                    visibility = AdVisibility.REGISTERED_ONLY,
                    id = "888"
                )
            ))
            .exchange()
            .expectStatus().isOk
            .expectBody(AdUpdateResponse::class.java).consumeWith {
                val dat = it.responseBody
                assertThat(dat?.ad?.id).isEqualTo("888")
                assertThat(dat?.ad?.title).isEqualTo("Title3")
                assertThat(dat?.ad?.description).isEqualTo("Description3")
                assertThat(dat?.ad?.address).isEqualTo("Address3")
                assertThat(dat?.ad?.dealside).isEqualTo(DealSide.PROPOSAL)
                assertThat(dat?.ad?.renttype).isEqualTo(RentType.LONG)
                assertThat(dat?.ad?.realtyproperty).isEqualTo(RealtyProperty.ROOM)
                assertThat(dat?.ad?.visibility).isEqualTo(AdVisibility.REGISTERED_ONLY)
            }
    }

    @Test
    fun delete() {
        println("PORT: $port")
        webTestClient
            .post()
            .uri("http://127.0.0.1:$port/ad/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(
                AdDeleteRequest(
                    requestType = "delete",
                    debug = AdDebug(mode = AdRequestDebugMode.STUB, stub = AdRequestDebugStubs.SUCCESS),
                    ad = BaseAdIdRequestAd(
                        id = "666"
                    )
                )
            )
            .exchange()
            .expectStatus().isOk
    }
}
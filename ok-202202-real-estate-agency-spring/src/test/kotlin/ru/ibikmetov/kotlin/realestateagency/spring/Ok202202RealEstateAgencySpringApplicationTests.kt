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
                assertThat(dat?.ad?.id).isEqualTo("1")
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
                        id = "1"
                    )
                )
            )
            .exchange()
            .expectStatus().isOk
            .expectBody(AdReadResponse::class.java).consumeWith {
                val dat = it.responseBody
                assertThat(dat?.ad?.id).isEqualTo("1")
                assertThat(dat?.ad?.title).isEqualTo("Требуется квартира")
                assertThat(dat?.ad?.description).isEqualTo("Требуется небольшая квартира для студента, желательно недалеко от университета")
                assertThat(dat?.ad?.address).isEqualTo("Ленина")
                assertThat(dat?.ad?.dealside).isEqualTo(DealSide.DEMAND)
                assertThat(dat?.ad?.renttype).isEqualTo(RentType.LONG)
                assertThat(dat?.ad?.realtyproperty).isEqualTo(RealtyProperty.FLAT)
                assertThat(dat?.ad?.visibility).isEqualTo(AdVisibility.PUBLIC)
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
                    title = "Требуется квартира",
                    description = "Требуется небольшая квартира для студента, желательно недалеко от университета",
                    address = "Ленина",
                    dealside = DealSide.DEMAND,
                    renttype = RentType.LONG,
                    realtyproperty = RealtyProperty.FLAT,
                    visibility = AdVisibility.PUBLIC,
                    id = "1"
                )
            ))
            .exchange()
            .expectStatus().isOk
            .expectBody(AdUpdateResponse::class.java).consumeWith {
                val dat = it.responseBody
                assertThat(dat?.ad?.id).isEqualTo("1")
                assertThat(dat?.ad?.title).isEqualTo("Требуется квартира")
                assertThat(dat?.ad?.description).isEqualTo("Требуется небольшая квартира для студента, желательно недалеко от университета")
                assertThat(dat?.ad?.address).isEqualTo("Ленина")
                assertThat(dat?.ad?.dealside).isEqualTo(DealSide.DEMAND)
                assertThat(dat?.ad?.renttype).isEqualTo(RentType.LONG)
                assertThat(dat?.ad?.realtyproperty).isEqualTo(RealtyProperty.FLAT)
                assertThat(dat?.ad?.visibility).isEqualTo(AdVisibility.PUBLIC)
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
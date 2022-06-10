import org.junit.Test
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.*
import kotlin.test.assertEquals

class DeserializationTest {

    @Test
    fun deserTest() {
        val json = """{"requestType":"create","requestType":null,"requestId":null,
            "debug":null,"ad":{"title":"Title","description":"Description","address":"Address",
            "dealside":"demand","renttype":"daily","realtyproperty":"storage","visibility":"public"}}""".trimMargin()

        val createRequest = jacksonMapper.readValue(json, IRequest::class.java) as AdCreateRequest
        println(createRequest)
        assertEquals("""Title""", createRequest.ad?.title)
        assertEquals("""Description""", createRequest.ad?.description)
        assertEquals("""Address""", createRequest.ad?.address)
        assertEquals(DealSide.DEMAND, createRequest.ad?.dealside)
        assertEquals(RentType.DAILY, createRequest.ad?.renttype)
        assertEquals(RealtyProperty.STORAGE, createRequest.ad?.realtyproperty)
        assertEquals(AdVisibility.PUBLIC, createRequest.ad?.visibility)
    }
}
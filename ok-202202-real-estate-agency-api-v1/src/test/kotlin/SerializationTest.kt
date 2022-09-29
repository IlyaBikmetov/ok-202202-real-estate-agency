import org.junit.Test
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.*
import ru.ibikmetov.kotlin.realestateagency.api.v1.*
import kotlin.test.assertContains

class SerializationTest {

    @Test
    fun serTest() {
        val createRequest = AdCreateRequest(
            ad = AdCreateObject(
                title = "Title",
                description = "Description",
                address = "Address",
                dealside = DealSide.DEMAND,
                renttype = RentType.DAILY,
                visibility = AdVisibility.PUBLIC
            )
        )
        val jsonString = jacksonMapper.writeValueAsString(createRequest)
        println(jsonString)
        assertContains(jsonString, """"title":"Title"""")
        assertContains(jsonString, """"description":"Description"""")
        assertContains(jsonString, """"address":"Address"""")
        assertContains(jsonString, """"dealside":"demand"""")
        assertContains(jsonString, """"renttype":"daily"""")
        assertContains(jsonString, """"realtyproperty":"storage"""")
        assertContains(jsonString, """"visibility":"public"""")
    }
}

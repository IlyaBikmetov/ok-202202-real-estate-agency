package ru.ibikmetov.kotlin.realestateagency.cassandra

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgDealSide
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgRentType
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgUserId
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdFilterRequest
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdIdRequest
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdRequest
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgAdStubFlats
import kotlin.test.assertEquals

class CassandraTest {
    @Test
    fun create() = runBlocking {
        val repo = InitCassandra.repository()
        val flat1 = ReAgAdStubFlats.AD_DEMAND_FLAT
        val response = repo.createAd(DbAdRequest(flat1))
        val response2 = repo.readAd(DbAdIdRequest(response.result!!.id, response.result!!.lock))
        if (response2.isSuccess) {
            val flat2 = response2.result!!
            assertEquals(flat1.title, flat2.title)
            assertEquals(flat1.description, flat2.description)
            assertEquals(flat1.address, flat2.address)
            assertEquals(flat1.ownerId, flat2.ownerId)
            assertEquals(flat1.visibility, flat2.visibility)
            assertEquals(flat1.dealSide, flat2.dealSide)
            assertEquals(flat1.rentType, flat2.rentType)
            assertEquals(flat1.realty, flat2.realty)
            repo.deleteAd(DbAdIdRequest(flat2.id, flat2.lock))
        }
    }

    @Test
    fun update() = runBlocking {
        val repo = InitCassandra.repository()
        var flat1 = ReAgAdStubFlats.AD_PROPOSAL_FLAT
        val response = repo.createAd(DbAdRequest(flat1))
        flat1 = response.result!!
        println("flat1.response.result: ${response.result!!}")
        flat1.rentType = ReAgRentType.LONG
        val response2 = repo.updateAd(DbAdRequest(flat1))
        println("flat1.response2.result: ${response2.result!!}")
        if (response2.isSuccess) {
            val response3 = repo.readAd(DbAdIdRequest(response2.result!!.id, response2.result!!.lock))
            if (response3.isSuccess) {
                val flat2 = response3.result!!
                assertEquals(flat1.title, flat2.title)
                assertEquals(flat1.description, flat2.description)
                assertEquals(flat1.address, flat2.address)
                assertEquals(flat1.ownerId, flat2.ownerId)
                assertEquals(flat1.visibility, flat2.visibility)
                assertEquals(flat1.dealSide, flat2.dealSide)
                assertEquals(flat1.rentType, flat2.rentType)
                assertEquals(flat1.realty, flat2.realty)
                //repo.deleteAd(DbAdIdRequest(flat2.id, flat2.lock))
            }
        }
    }

    @Test
    fun search() = runBlocking {
        val repo = InitCassandra.repository()
        var flat1 = ReAgAdStubFlats.AD_PROPOSAL_FLAT
        var flat2 = ReAgAdStubFlats.AD_DEMAND_FLAT
        val response1 = repo.createAd(DbAdRequest(flat1))
        val response2 = repo.createAd(DbAdRequest(flat2))
        if (response1.isSuccess && response2.isSuccess) {
            val filter = DbAdFilterRequest("")
            val response3 = repo.searchAd(filter)
            if (response3.isSuccess && response3.result!!.isNotEmpty()) {
                val flat3 = response3.result!![0]
                println("flat3: $flat3")
                assertEquals(flat2.title, flat3.title)
                assertEquals(flat2.description, flat3.description)
                assertEquals(flat2.address, flat3.address)
                assertEquals(flat2.ownerId, flat3.ownerId)
                assertEquals(flat2.visibility, flat3.visibility)
                assertEquals(flat2.dealSide, flat3.dealSide)
                assertEquals(flat2.rentType, flat3.rentType)
                assertEquals(flat2.realty, flat3.realty)
                //repo.deleteAd(DbAdIdRequest(flat1.id, flat1.lock))
                //repo.deleteAd(DbAdIdRequest(flat2.id, flat2.lock))
            }
        }
    }
}
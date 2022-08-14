package ru.ibikmetov.kotlin.realestateagency.inmemory

import org.junit.Test
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdIdRequest
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdRequest
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgAdStubFlats
import kotlinx.coroutines.*
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgDealSide
import kotlin.test.assertEquals

class InMemoryTest {
    @Test
    fun createInMemory() = runBlocking {
        val inMemoryRepo = AdRepoInMemory()
        val flat1 = ReAgAdStubFlats.AD_DEMAND_FLAT
        val responseCreate = inMemoryRepo.createAd(DbAdRequest(flat1))
        val flat2 = inMemoryRepo.readAd(DbAdIdRequest(responseCreate.result!!.id)).result!!
        assertEquals(1, inMemoryRepo.count())
        assertEquals(flat1.title, flat2.title)
        assertEquals(flat1.description, flat2.description)
        assertEquals(flat1.address, flat2.address)
        assertEquals(flat1.ownerId, flat2.ownerId)
        assertEquals(flat1.dealSide, flat2.dealSide)
        assertEquals(flat1.rentType, flat2.rentType)
        assertEquals(flat1.visibility, flat2.visibility)
        assertEquals(flat1.realty, flat2.realty)

        inMemoryRepo.deleteAd(DbAdIdRequest(flat2.id, flat2.lock))
        assertEquals(0, inMemoryRepo.count())
    }

    @Test
    fun updateInMemory() = runBlocking {
        val inMemoryRepo = AdRepoInMemory()
        val flat1 = ReAgAdStubFlats.AD_DEMAND_FLAT
        val responseCreate = inMemoryRepo.createAd(DbAdRequest(flat1))
        val flat2 = inMemoryRepo.readAd(DbAdIdRequest(responseCreate.result!!.id)).result!!
        flat2.dealSide = ReAgDealSide.PROPOSAL
        val responseUpdate = inMemoryRepo.updateAd(DbAdRequest(flat2))
        val flat3 = inMemoryRepo.readAd(DbAdIdRequest(responseUpdate.result!!.id)).result!!
        assertEquals(1, inMemoryRepo.count())
        assertEquals(flat1.title, flat3.title)
        assertEquals(flat1.description, flat3.description)
        assertEquals(flat1.address, flat3.address)
        assertEquals(flat1.ownerId, flat3.ownerId)
        assertEquals(ReAgDealSide.PROPOSAL, flat3.dealSide)
        assertEquals(flat1.rentType, flat3.rentType)
        assertEquals(flat1.visibility, flat3.visibility)
        assertEquals(flat1.realty, flat3.realty)

        inMemoryRepo.deleteAd(DbAdIdRequest(flat3.id, flat3.lock))
        assertEquals(0, inMemoryRepo.count())
    }

}
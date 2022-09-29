package ru.ibikmetov.kotlin.realestateagency.cassandra

import kotlinx.coroutines.runBlocking
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAd
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAdId
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAdLock
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgRentType
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyFlat
import ru.ibikmetov.kotlin.realestateagency.common.models.realty.ReAgAdRealtyStoveType
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdIdRequest
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdRequest
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgAdStubFlats

fun main() = runBlocking {
    val repo = InitCassandra.repository()
    var ad = ReAgAd(
        id = ReAgAdId("279a1782-b0fe-43d9-9ffd-814aa7540495"),
    )
    var flat1 = repo.readAd(DbAdIdRequest(ad))
    println("1: flat1: $flat1")
    ad = flat1.result!!
    ad.rentType = ReAgRentType.LONG
    ad.realty = ReAgAdRealtyFlat(
        square = 444F,
        floor = 333,
        cntRooms = 33,
        stoveType = ReAgAdRealtyStoveType.ELECTRIC,
        balcony = false,
    )
    val flat2 = repo.updateAd(DbAdRequest(ad))
    println("2: flat2: $flat2")
}
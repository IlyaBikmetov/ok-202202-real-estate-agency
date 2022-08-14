package ru.ibikmetov.kotlin.realestateagency.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.internal.core.type.codec.extras.enums.EnumNameCodec
import com.datastax.oss.driver.internal.core.type.codec.registry.DefaultCodecRegistry
import com.datastax.oss.driver.internal.core.util.concurrent.CompletableFutures
import kotlinx.coroutines.runBlocking
import ru.ibikmetov.kotlin.realestateagency.cassandra.dto.*
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAd
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdIdRequest
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdRequest
import ru.ibikmetov.kotlin.realestateagency.common.stubs.ReAgAdStubFlats
import java.net.InetSocketAddress

fun main() = runBlocking {
    val repo = Init.repository(emptyList(), "real_estate_agency")
    val flat1 = ReAgAdStubFlats.AD_DEMAND_FLAT
    val responseCreate = repo.createAd(DbAdRequest(flat1))
    println("Result: ${responseCreate.isSuccess}")
    if (responseCreate.isSuccess) {
        val flat2 = repo.readAd(DbAdIdRequest(responseCreate.result!!.id, responseCreate.result!!.lock)).result
        if (flat2 != null) {
            println("Description: ${flat2.description}")
        }
    }
}

object Init {
    private val codecRegistry by lazy {
        DefaultCodecRegistry("default").apply {
            register(EnumNameCodec(AdVisibility::class.java))
            register(EnumNameCodec(AdDealSide::class.java))
            register(EnumNameCodec(AdRentType::class.java))
            register(EnumNameCodec(AdRealtyProperty::class.java))
        }
    }

    private val session by lazy {
        CqlSession.builder()
            .addContactPoint(InetSocketAddress("localhost", 9042))
            .withLocalDatacenter("datacenter1")
            .withAuthCredentials(null.toString(), null.toString())
            .withCodecRegistry(codecRegistry)
            .build()
    }

    private val mapper by lazy { CassandraMapper.builder(session).build() }

    private fun createSchema(keyspace: String) {
        session.execute(
            SchemaBuilder
                .createKeyspace(keyspace)
                .ifNotExists()
                .withSimpleStrategy(1)
                .build()
        )
        session.execute(AdCassandraDTO.table(keyspace, AdCassandraDTO.TABLE_NAME))
        //session.execute(AdCassandraDTO.titleIndex(keyspace, AdCassandraDTO.TABLE_NAME))
    }

    fun repository(initObjects: List<ReAgAd>, keyspace: String): RepoAdCassandra {
        createSchema(keyspace)
        val dao = mapper.adDao(keyspace, AdCassandraDTO.TABLE_NAME)
        CompletableFutures
            .allDone(initObjects.map { dao.create(AdCassandraDTO(it)) })
            .toCompletableFuture()
            .get()
        return RepoAdCassandra(dao)
    }
}
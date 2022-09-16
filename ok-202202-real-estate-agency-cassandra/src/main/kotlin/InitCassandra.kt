package ru.ibikmetov.kotlin.realestateagency.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.internal.core.type.codec.extras.enums.EnumNameCodec
import com.datastax.oss.driver.internal.core.type.codec.registry.DefaultCodecRegistry
import com.datastax.oss.driver.internal.core.util.concurrent.CompletableFutures
import ru.ibikmetov.kotlin.realestateagency.cassandra.dto.*
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAd
import java.net.InetSocketAddress
import java.util.*

object InitCassandra {
    private val props = javaClass.classLoader.getResourceAsStream("config.properties").use {
        Properties().apply { load(it) }
    }

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
            .addContactPoint(InetSocketAddress(props["server"].toString(), props["port"].toString().toInt()))
            .withLocalDatacenter(props["datacenter"].toString())
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
        session.execute(AdCassandraDTO.descrIndex(keyspace, AdCassandraDTO.TABLE_NAME))
    }

    fun repository(initObjects: List<ReAgAd>): RepoAdCassandra {
        createSchema(props["keyspace"].toString())
        val dao = mapper.adDao(props["keyspace"].toString(), AdCassandraDTO.TABLE_NAME)
        CompletableFutures
            .allDone(initObjects.map { dao.create(AdCassandraDTO(it)) })
            .toCompletableFuture()
            .get()
        return RepoAdCassandra(dao)
    }

    fun keySpace() = props["keyspace"].toString()
}
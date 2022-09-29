package ru.ibikmetov.kotlin.realestateagency.async.kafka

import ru.ibikmetov.kotlin.realestateagency.cassandra.InitCassandra
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgSettings
import ru.ibikmetov.kotlin.realestateagency.spring.service.AdService

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(
        config,
        listOf(ConsumerStrategyV1()),
        AdService(ReAgSettings(InitCassandra.repository())),
    )
    consumer.run()
}
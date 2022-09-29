package ru.ibikmetov.kotlin.realestateagency.async.kafka

import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.ibikmetov.kotlin.realestateagency.api.v1.apiV1RequestSerialize
import ru.ibikmetov.kotlin.realestateagency.api.v1.apiV1ResponseDeserialize
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdCreateObject
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdCreateRequest
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdCreateResponse
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdDebug
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdRequestDebugMode
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdRequestDebugStubs
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdVisibility
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.DealSide
import java.util.*
import kotlin.test.assertEquals


class KafkaTest {
    @Test
    fun runKafka() = runBlocking {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInV1
        val outputTopic = config.kafkaTopicOutV1

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(AdCreateRequest(
                        requestId = "111",
                        ad = AdCreateObject(
                            title = "Some Ad",
                            description = "some testing ad to check them all",
                            visibility = AdVisibility.OWNER_ONLY,
                            dealside = DealSide.DEMAND
                        ),
                        debug = AdDebug(
                            mode = AdRequestDebugMode.STUB,
                            stub = AdRequestDebugStubs.SUCCESS
                        )
                    ))
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, 0)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<AdCreateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("111", result.requestId)
        assertEquals("Some Ad", result.ad?.title)
    }

    companion object {
        const val PARTITION = 0
    }
}
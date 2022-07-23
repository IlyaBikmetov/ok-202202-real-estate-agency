package ru.ibikmetov.kotlin.realestateagency.async.kafka

import ru.ibikmetov.kotlin.realestateagency.api.v1.apiV1RequestDeserialize
import ru.ibikmetov.kotlin.realestateagency.api.v1.apiV1ResponseSerialize
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.IRequest
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.IResponse
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.mappers.fromTransport
import ru.ibikmetov.kotlin.realestateagency.mappers.toTransportAd

class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: ReAgContext): String {
        val response: IResponse = source.toTransportAd()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: ReAgContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}
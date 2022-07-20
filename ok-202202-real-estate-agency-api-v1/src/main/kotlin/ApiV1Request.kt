package ru.ibikmetov.kotlin.realestateagency.api.v1

import ru.ibikmetov.kotlin.realestateagency.api.v1.models.*

fun apiV1RequestSerialize(request: IRequest): String = jacksonMapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> apiV1RequestDeserialize(json: String): T =
    jacksonMapper.readValue(json, IRequest::class.java) as T
/**
 * Real estate agency
 *
 * Сервис для сдачи и аренды недвижимости
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ru.ibikmetov.kotlin.realestateagency.api.v1.models

import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdCreateObject
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdCreateRequestAllOf
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdDebug
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdRequestDebug
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.IRequest

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 *
 * @param requestType Поле-дескриминатор для вычисления типа запроса
 * @param requestId Идентификатор запроса для отладки
 * @param debug 
 * @param ad 
 */

data class AdCreateRequest (

    /* Поле-дескриминатор для вычисления типа запроса */
    @field:JsonProperty("requestType")
    override val requestType: kotlin.String? = null,

    /* Идентификатор запроса для отладки */
    @field:JsonProperty("requestId")
    override val requestId: kotlin.String? = null,

    @field:JsonProperty("debug")
    val debug: AdDebug? = null,

    @field:JsonProperty("ad")
    val ad: AdCreateObject? = null

) : IRequest


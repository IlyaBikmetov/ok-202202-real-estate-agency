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

import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdResponseObject
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdSearchResponseAllOf
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.Error
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.IResponse
import ru.ibikmetov.kotlin.realestateagency.api.v1.models.ResponseResult

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 *
 * @param responseType Поле-дескриминатор для вычисления типа запроса
 * @param requestId Идентификатор запроса для отладки
 * @param result 
 * @param errors 
 * @param ads 
 */

data class AdSearchResponse (

    /* Поле-дескриминатор для вычисления типа запроса */
    @field:JsonProperty("responseType")
    override val responseType: kotlin.String? = null,

    /* Идентификатор запроса для отладки */
    @field:JsonProperty("requestId")
    override val requestId: kotlin.String? = null,

    @field:JsonProperty("result")
    override val result: ResponseResult? = null,

    @field:JsonProperty("errors")
    override val errors: kotlin.collections.List<Error>? = null,

    @field:JsonProperty("ads")
    val ads: kotlin.collections.List<AdResponseObject>? = null

) : IResponse

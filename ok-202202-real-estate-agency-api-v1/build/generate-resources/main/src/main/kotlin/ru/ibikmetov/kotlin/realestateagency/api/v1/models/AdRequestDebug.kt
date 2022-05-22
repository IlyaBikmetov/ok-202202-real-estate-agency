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

import ru.ibikmetov.kotlin.realestateagency.api.v1.models.AdDebug

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 *
 * @param debug 
 */

data class AdRequestDebug (

    @field:JsonProperty("debug")
    val debug: AdDebug? = null

)


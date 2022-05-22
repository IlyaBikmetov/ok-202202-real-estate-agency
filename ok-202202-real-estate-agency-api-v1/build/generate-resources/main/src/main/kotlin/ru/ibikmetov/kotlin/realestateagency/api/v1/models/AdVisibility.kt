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


import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Тип видимости объявления. Возможные значения: видит только владелец, только зарегистрированный в системе пользователь, видимо всем
 *
 * Values: OWNER_ONLY,REGISTERED_ONLY,PUBLIC
 */

enum class AdVisibility(val value: kotlin.String) {

    @JsonProperty(value = "ownerOnly")
    OWNER_ONLY("ownerOnly"),

    @JsonProperty(value = "registeredOnly")
    REGISTERED_ONLY("registeredOnly"),

    @JsonProperty(value = "public")
    PUBLIC("public");

    /**
     * Override toString() to avoid using the enum variable name as the value, and instead use
     * the actual value defined in the API spec file.
     *
     * This solves a problem when the variable name and its value are different, and ensures that
     * the client sends the correct enum values to the server always.
     */
    override fun toString(): String = value

    companion object {
        /**
         * Converts the provided [data] to a [String] on success, null otherwise.
         */
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is AdVisibility) "$data" else null

        /**
         * Returns a valid [AdVisibility] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): AdVisibility? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}


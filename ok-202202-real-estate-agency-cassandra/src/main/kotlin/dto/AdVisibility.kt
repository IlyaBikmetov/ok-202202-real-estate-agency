package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgVisibility

enum class AdVisibility {
    VISIBLE_TO_OWNER,
    VISIBLE_TO_GROUP,
    VISIBLE_PUBLIC,
}

fun AdVisibility?.fromTransport() = when(this) {
    null -> ReAgVisibility.NONE
    AdVisibility.VISIBLE_TO_OWNER -> ReAgVisibility.VISIBLE_TO_OWNER
    AdVisibility.VISIBLE_TO_GROUP -> ReAgVisibility.VISIBLE_TO_GROUP
    AdVisibility.VISIBLE_PUBLIC -> ReAgVisibility.VISIBLE_PUBLIC
}

fun ReAgVisibility.toTransport() = when(this) {
    ReAgVisibility.NONE -> null
    ReAgVisibility.VISIBLE_TO_OWNER -> AdVisibility.VISIBLE_TO_OWNER
    ReAgVisibility.VISIBLE_TO_GROUP -> AdVisibility.VISIBLE_TO_GROUP
    ReAgVisibility.VISIBLE_PUBLIC -> AdVisibility.VISIBLE_PUBLIC
}
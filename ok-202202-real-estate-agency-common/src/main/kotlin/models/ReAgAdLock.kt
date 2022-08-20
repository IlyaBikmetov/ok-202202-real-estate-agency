package ru.ibikmetov.kotlin.realestateagency.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class ReAgAdLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ReAgAdLock("")
    }
}
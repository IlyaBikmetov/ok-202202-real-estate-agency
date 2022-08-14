package ru.ibikmetov.kotlin.realestateagency.common.repository

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgError

interface IDbResponse<T> {
    val result: T?
    val isSuccess: Boolean
    val errors: List<ReAgError>
}
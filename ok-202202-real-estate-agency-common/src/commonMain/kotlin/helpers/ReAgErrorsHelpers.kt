package ru.ibikmetov.kotlin.realestateagency.common.helpers

import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgError
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState

fun Throwable.asReAgError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = ReAgError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun ReAgContext.addError(error: ReAgError) = errors.add(error)
fun ReAgContext.fail(error: ReAgError) {
    addError(error)
    state = ReAgState.FAILING
}
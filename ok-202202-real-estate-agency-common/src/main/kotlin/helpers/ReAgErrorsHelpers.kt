package ru.ibikmetov.kotlin.realestateagency.common.helpers

import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgError
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgErrorLevels
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

fun errorMapping(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: ReAgErrorLevels = ReAgErrorLevels.ERROR,
) = ReAgError(
    code = "mapping-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: ReAgErrorLevels = ReAgErrorLevels.ERROR,
) = ReAgError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorConcurrency(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: ReAgErrorLevels = ReAgErrorLevels.ERROR,
) = ReAgError(
    field = "lock",
    code = "concurrent-$violationCode",
    group = "concurrency",
    message = "Concurrent object access error: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    level: ReAgErrorLevels = ReAgErrorLevels.ERROR,
) = ReAgError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
)
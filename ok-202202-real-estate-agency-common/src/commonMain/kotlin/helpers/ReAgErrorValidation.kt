package ru.ibikmetov.kotlin.realestateagency.common.helpers

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgError
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgErrorLevels

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
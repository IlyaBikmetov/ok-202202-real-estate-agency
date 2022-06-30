package ru.ibikmetov.kotlin.realestateagency.business.validation

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.helpers.errorValidation
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.helpers.fail

fun ICorChainDsl<ReAgContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { adValidating.id.asString().isEmpty() }
    handle {
        fail(errorValidation(
            field = "id",
            violationCode = "empty",
            description = "field must not be empty"
        ))
    }
}
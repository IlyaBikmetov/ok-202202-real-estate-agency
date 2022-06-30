package ru.ibikmetov.kotlin.realestateagency.business.validation

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.helpers.errorValidation
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.helpers.fail

fun ICorChainDsl<ReAgContext>.validateDescriptionHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { adValidating.description.isNotEmpty() && ! adValidating.description.contains(regExp) }
    handle {
        fail(errorValidation(
            field = "description",
            violationCode = "noContent",
            description = "field must contain leters"
        ))
    }
}
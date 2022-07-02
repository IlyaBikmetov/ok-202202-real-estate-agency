package ru.ibikmetov.kotlin.realestateagency.business.validation

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.helpers.errorValidation
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.helpers.fail
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAdId

fun ICorChainDsl<ReAgContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { adValidating.id != ReAgAdId.NONE && ! adValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = adValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(errorValidation(
            field = "id",
            violationCode = "badFormat",
            description = "value $encodedId must contain only"
        ))
    }
}
package ru.ibikmetov.kotlin.realestateagency.business.permission

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.helpers.fail
import ru.ibikmetov.kotlin.realestateagency.common.models.*

fun ICorChainDsl<ReAgContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == ReAgState.RUNNING }
    worker("Вычисление отношения объявления к принципалу") {
        adRepoRead.principalRelations = adRepoRead.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к объявлению") {
        permitted = adRepoRead.principalRelations.asSequence().flatMap { relation ->
            chainPermissions.map { permission ->
                AccessTableConditions(
                    command = command,
                    permission = permission,
                    relation = relation,
                )
            }
        }.any {
            accessTable[it] ?: false
        }
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(ReAgError(message = "User is not allowed to this operation"))
        }
    }
}

private fun ReAgAd.resolveRelationsTo(principal: ReAgPrincipalModel): Set<ReAgPrincipalRelations> = setOfNotNull(
    ReAgPrincipalRelations.NONE,
    ReAgPrincipalRelations.OWN.takeIf { principal.id == ownerId },
    ReAgPrincipalRelations.PUBLIC.takeIf { visibility == ReAgVisibility.VISIBLE_PUBLIC },
    ReAgPrincipalRelations.MODERATOR.takeIf { visibility != ReAgVisibility.VISIBLE_TO_OWNER },
)
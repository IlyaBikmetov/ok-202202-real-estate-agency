package ru.ibikmetov.kotlin.realestateagency.business.permission

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.*

fun ICorChainDsl<ReAgContext>.chainPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление прав доступа для групп пользователей"

    on {
        state == ReAgState.RUNNING
    }

    handle {
        val permAdd: Set<ReAgUserPermissions> = principal.groups.map {
            when(it) {
                ReAgUserGroups.USER -> setOf(
                    ReAgUserPermissions.CREATE_OWN,
                    ReAgUserPermissions.READ_OWN,
                    ReAgUserPermissions.UPDATE_OWN,
                    ReAgUserPermissions.DELETE_OWN,
                    ReAgUserPermissions.SEARCH_OWN,
                    ReAgUserPermissions.SEARCH_GROUP,
                    ReAgUserPermissions.SEARCH_PUBLIC,
                )
                ReAgUserGroups.MODERATOR_MP -> ReAgUserPermissions.values().toSet()
                ReAgUserGroups.ADMIN_AD -> ReAgUserPermissions.values().toSet()
                ReAgUserGroups.TEST -> ReAgUserPermissions.values().toSet()
                ReAgUserGroups.BAN_AD -> setOf(
                    ReAgUserPermissions.SEARCH_OWN,
                    ReAgUserPermissions.SEARCH_GROUP,
                    ReAgUserPermissions.SEARCH_PUBLIC,
                )
            }
        }.flatten().toSet()
        val permDel: Set<ReAgUserPermissions> = principal.groups.map {
            when(it) {
                ReAgUserGroups.USER -> setOf<ReAgUserPermissions>()
                ReAgUserGroups.MODERATOR_MP -> setOf<ReAgUserPermissions>()
                ReAgUserGroups.ADMIN_AD -> setOf<ReAgUserPermissions>()
                ReAgUserGroups.TEST -> setOf<ReAgUserPermissions>()
                ReAgUserGroups.BAN_AD -> setOf<ReAgUserPermissions>()
            }
        }.flatten().toSet()
        chainPermissions.addAll(permAdd)
        chainPermissions.removeAll(permDel)
        //println("PRINCIPAL: $principal")
        //println("PERMISSIONS: $chainPermissions")
    }
}
package ru.ibikmetov.kotlin.realestateagency.business.permission

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAdPermissionClient
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgState
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgUserGroups

fun ICorChainDsl<ReAgContext>.frontPermissions(title: String) = chain {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { state == ReAgState.RUNNING }

    worker {
        this.title = "Разрешения для собственного объявления"
        description = this.title
        on { adRepoDone.ownerId == principal.id }
        handle {
            val permAdd: Set<ReAgAdPermissionClient> = principal.groups.map {
                when (it) {
                    ReAgUserGroups.USER -> setOf(
                        ReAgAdPermissionClient.READ,
                        ReAgAdPermissionClient.UPDATE,
                        ReAgAdPermissionClient.DELETE,
                    )
                    ReAgUserGroups.MODERATOR_MP -> setOf()
                    ReAgUserGroups.ADMIN_AD -> setOf()
                    ReAgUserGroups.TEST -> setOf()
                    ReAgUserGroups.BAN_AD -> setOf()
                }
            }.flatten().toSet()
            val permDel: Set<ReAgAdPermissionClient> = principal.groups.map {
                when (it) {
                    ReAgUserGroups.USER -> setOf()
                    ReAgUserGroups.MODERATOR_MP -> setOf()
                    ReAgUserGroups.ADMIN_AD -> setOf()
                    ReAgUserGroups.TEST -> setOf()
                    ReAgUserGroups.BAN_AD -> setOf(
                        ReAgAdPermissionClient.UPDATE,
                        ReAgAdPermissionClient.DELETE,
                    )
                }
            }.flatten().toSet()
            adRepoDone.permissionsClient.addAll(permAdd)
            adRepoDone.permissionsClient.removeAll(permDel)
        }
    }

    worker {
        this.title = "Разрешения для модератора"
        description = this.title
        on { adRepoDone.ownerId != principal.id /* && tag, group, ... */ }
        handle {
            val permAdd: Set<ReAgAdPermissionClient> = principal.groups.map {
                when (it) {
                    ReAgUserGroups.USER -> setOf()
                    ReAgUserGroups.MODERATOR_MP -> setOf(
                        ReAgAdPermissionClient.READ,
                        ReAgAdPermissionClient.UPDATE,
                        ReAgAdPermissionClient.DELETE,
                    )
                    ReAgUserGroups.ADMIN_AD -> setOf()
                    ReAgUserGroups.TEST -> setOf()
                    ReAgUserGroups.BAN_AD -> setOf()
                }
            }.flatten().toSet()
            val permDel: Set<ReAgAdPermissionClient> = principal.groups.map {
                when (it) {
                    ReAgUserGroups.USER -> setOf(
                        ReAgAdPermissionClient.UPDATE,
                        ReAgAdPermissionClient.DELETE,
                    )
                    ReAgUserGroups.MODERATOR_MP -> setOf()
                    ReAgUserGroups.ADMIN_AD -> setOf()
                    ReAgUserGroups.TEST -> setOf()
                    ReAgUserGroups.BAN_AD -> setOf(
                        ReAgAdPermissionClient.UPDATE,
                        ReAgAdPermissionClient.DELETE,
                    )
                }
            }.flatten().toSet()
            adRepoDone.permissionsClient.addAll(permAdd)
            adRepoDone.permissionsClient.removeAll(permDel)
        }
    }
    worker {
        this.title = "Разрешения для администратора"
        description = this.title
    }
}
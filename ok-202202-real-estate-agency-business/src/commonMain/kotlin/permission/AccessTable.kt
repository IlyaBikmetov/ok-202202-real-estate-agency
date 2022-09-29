package ru.ibikmetov.kotlin.realestateagency.business.permission

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgCommand
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgPrincipalRelations
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgUserPermissions

data class AccessTableConditions(
    val command: ReAgCommand,
    val permission: ReAgUserPermissions,
    val relation: ReAgPrincipalRelations
)

val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command = ReAgCommand.CREATE,
        permission = ReAgUserPermissions.CREATE_OWN,
        relation = ReAgPrincipalRelations.NONE
    ) to true,

    // Read
    AccessTableConditions(
        command = ReAgCommand.READ,
        permission = ReAgUserPermissions.READ_OWN,
        relation = ReAgPrincipalRelations.OWN
    ) to true,
    AccessTableConditions(
        command = ReAgCommand.READ,
        permission = ReAgUserPermissions.READ_PUBLIC,
        relation = ReAgPrincipalRelations.PUBLIC
    ) to true,

    // Update
    AccessTableConditions(
        command = ReAgCommand.UPDATE,
        permission = ReAgUserPermissions.UPDATE_OWN,
        relation = ReAgPrincipalRelations.OWN
    ) to true,

    // Delete
    AccessTableConditions(
        command = ReAgCommand.DELETE,
        permission = ReAgUserPermissions.DELETE_OWN,
        relation = ReAgPrincipalRelations.OWN
    ) to true,
)
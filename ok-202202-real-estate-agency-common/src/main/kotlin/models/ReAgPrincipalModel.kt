package ru.ibikmetov.kotlin.realestateagency.common.models

data class ReAgPrincipalModel(
    val id: ReAgUserId = ReAgUserId.NONE,
    val fName: String = "",
    val mName: String = "",
    val lName: String = "",
    val groups: Set<ReAgUserGroups> = emptySet()
) {
    companion object {
        val NONE = ReAgPrincipalModel()
    }
}
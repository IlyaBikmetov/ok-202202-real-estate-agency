package ru.ibikmetov.kotlin.realestateagency.common.models

enum class ReAgUserPermissions {
    CREATE_OWN,
    CREATE_GROUP,
    CREATE_PUBLIC,

    READ_OWN,
    READ_GROUP,
    READ_PUBLIC,

    UPDATE_OWN,
    UPDATE_GROUP,
    UPDATE_PUBLIC,

    DELETE_OWN,
    DELETE_GROUP,
    DELETE_PUBLIC,

    SEARCH_OWN,
    SEARCH_GROUP,
    SEARCH_PUBLIC,
}
package ru.ibikmetov.kotlin.realestateagency.common.models

import ru.ibikmetov.kotlin.realestateagency.common.repository.IAdRepository

data class ReAgSettings(
    val repo: IAdRepository = IAdRepository.NONE,
)
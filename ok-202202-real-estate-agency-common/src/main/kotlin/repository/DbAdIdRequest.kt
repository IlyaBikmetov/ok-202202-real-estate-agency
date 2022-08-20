package ru.ibikmetov.kotlin.realestateagency.common.repository

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAd
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAdId
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAdLock

data class DbAdIdRequest(
    val id: ReAgAdId,
    val lock: ReAgAdLock = ReAgAdLock.NONE,
) {
    constructor(ad: ReAgAd): this(ad.id, ad.lock)
}
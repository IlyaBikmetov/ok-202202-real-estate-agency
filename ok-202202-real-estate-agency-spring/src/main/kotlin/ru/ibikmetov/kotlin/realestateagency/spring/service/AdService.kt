package ru.ibikmetov.kotlin.realestateagency.spring.service

import org.springframework.stereotype.Service
import ru.ibikmetov.kotlin.realestateagency.business.ReAgAdProcessor
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgSettings

@Service
class AdService(settings: ReAgSettings = ReAgSettings()) {
    private val processor = ReAgAdProcessor(settings)

    suspend fun exec(context: ReAgContext) = processor.exec(context)

    suspend fun createAd(context: ReAgContext) = processor.exec(context)
    suspend fun readAd(context: ReAgContext) = processor.exec(context)
    suspend fun updateAd(context: ReAgContext) = processor.exec(context)
    suspend fun deleteAd(context: ReAgContext) = processor.exec(context)
    suspend fun searchAd(context: ReAgContext) = processor.exec(context)
}

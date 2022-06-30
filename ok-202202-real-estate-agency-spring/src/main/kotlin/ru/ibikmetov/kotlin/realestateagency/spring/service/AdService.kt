package ru.ibikmetov.kotlin.realestateagency.spring.service

import org.springframework.stereotype.Service
import ru.ibikmetov.kotlin.realestateagency.business.ReAgAdProcessor
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext

@Service
class AdService {
    private val processor = ReAgAdProcessor()

    suspend fun exec(context: ReAgContext) = processor.exec(context)

    suspend fun createAd(context: ReAgContext) = processor.exec(context)
    suspend fun readAd(context: ReAgContext) = processor.exec(context)
    suspend fun updateAd(context: ReAgContext) = processor.exec(context)
    suspend fun deleteAd(context: ReAgContext) = processor.exec(context)
    suspend fun searchAd(context: ReAgContext) = processor.exec(context)
}

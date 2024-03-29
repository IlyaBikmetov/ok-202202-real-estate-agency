package ru.ibikmetov.kotlin.realestateagency.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.ibikmetov.kotlin.realestateagency.cassandra.InitCassandra
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgSettings
import ru.ibikmetov.kotlin.realestateagency.common.repository.IAdRepository
import ru.ibikmetov.kotlin.realestateagency.spring.service.AdService

@Configuration
class ServiceConfig {
   @Bean
    fun session() = InitCassandra.session()

    @Bean
    fun repo(): IAdRepository = InitCassandra.repository(emptyList())

    @Bean
    fun reAgSettings(): ReAgSettings = ReAgSettings(repo())

    @Bean
    fun serviceAd(settings: ReAgSettings): AdService = AdService(settings)
}
package ru.ibikmetov.kotlin.realestateagency.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.ibikmetov.kotlin.realestateagency.common.helpers.errorConcurrency
import ru.ibikmetov.kotlin.realestateagency.common.models.*
import ru.ibikmetov.kotlin.realestateagency.common.repository.*
import ru.ibikmetov.kotlin.realestateagency.inmemory.entities.AdEntity
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class AdRepoInMemory(
    initObjects: List<ReAgAd> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() }
) : IAdRepository {
    /**
     * Инициализация кеша с установкой "времени жизни" данных после записи
     */
    private val cache = Cache.Builder()
        .expireAfterWrite(ttl)
        .build<String, AdEntity>()
    private val mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(ad: ReAgAd) {
        val entity = AdEntity(ad)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    fun count() = cache.asMap().count()

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        val key = randomUuid()
        val ad = rq.ad.copy(id = ReAgAdId(key), lock = ReAgAdLock(randomUuid()))
        val entity = AdEntity(ad)
        mutex.withLock {
            cache.put(key, entity)
        }
        return DbAdResponse(
            result = ad,
            isSuccess = true,
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != ReAgAdId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbAdResponse(
                    result = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        val key = rq.ad.id.takeIf { it != ReAgAdId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.ad.lock.takeIf { it != ReAgAdLock.NONE }?.asString()
        val newAd = rq.ad.copy(lock = ReAgAdLock(randomUuid()))
        val entity = AdEntity(newAd)
        mutex.withLock {
            val local = cache.get(key)
            when {
                local == null -> return resultErrorNotFound
                local.lock == null || local.lock == oldLock -> cache.put(key, entity)
                else -> return resultErrorConcurrent
            }
        }
        return DbAdResponse(
            result = newAd,
            isSuccess = true,
        )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != ReAgAdId.NONE }?.asString() ?: return resultErrorEmptyId
        mutex.withLock {
            val local = cache.get(key) ?: return resultErrorNotFound
            return if (local.lock == rq.lock.asString()) {
                cache.invalidate(key)
                DbAdResponse(
                    result = local.toInternal(),
                    isSuccess = true,
                    errors = emptyList()
                )
            } else {
                resultErrorConcurrent
            }
        }
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.search.takeIf { it.isNotBlank()
                        && rq.dealSide == ReAgDealSide.NONE
                        && rq.rentType == ReAgRentType.NONE
                }?.let {
                    entry.value.description?.indexOf(it)!! >= 0
                } ?: true
            }
            .filter { entry ->
                rq.dealSide.takeIf { it != ReAgDealSide.NONE }?.let {
                    it.name == entry.value.dealSide
                } ?: true
            }
            .filter { entry ->
                rq.rentType.takeIf { it != ReAgRentType.NONE }?.let {
                    it.name == entry.value.rentType
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbAdsResponse(
            result = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbAdResponse(
            result = null,
            isSuccess = false,
            errors = listOf(
                ReAgError(
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorConcurrent = DbAdResponse(
            result = null,
            isSuccess = false,
            errors = listOf(
                errorConcurrency(
                    violationCode = "changed",
                    description = "Object has changed during request handling"
                ),
            )
        )
        val resultErrorNotFound = DbAdResponse(
            isSuccess = false,
            result = null,
            errors = listOf(
                ReAgError(
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
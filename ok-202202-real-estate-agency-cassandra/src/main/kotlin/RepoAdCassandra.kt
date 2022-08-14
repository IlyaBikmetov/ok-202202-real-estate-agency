package ru.ibikmetov.kotlin.realestateagency.cassandra

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withTimeout
import org.slf4j.LoggerFactory
import ru.ibikmetov.kotlin.realestateagency.cassandra.dto.AdCassandraDTO
import ru.ibikmetov.kotlin.realestateagency.common.helpers.asReAgError
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAdId
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgAdLock
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgError
import ru.ibikmetov.kotlin.realestateagency.common.repository.*

import java.util.concurrent.CompletionStage

class RepoAdCassandra(
    private val dao: AdCassandraDAO,
    private val timeoutMillis: Long = 30_000,
    private val randomUuid: () -> String = { uuid4().toString() }
) : IAdRepository {
    private val log = LoggerFactory.getLogger(javaClass)

    private fun errorToAdResponse(e: Exception) = DbAdResponse.error(e.asReAgError())
    private fun errorToAdsResponse(e: Exception) = DbAdsResponse.error(e.asReAgError())

    private suspend fun <DbRes, Response> doDbAction(
        name: String,
        daoAction: () -> CompletionStage<DbRes>,
        okToResponse: (DbRes) -> Response,
        errorToResponse: (Exception) -> Response
    ): Response =
        try {
            var dbRes = withTimeout(timeoutMillis) { daoAction().await() }
            okToResponse(dbRes)
        } catch (e: Exception) {
            log.error("Failed to $name", e)
            errorToResponse(e)
        }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        val new = rq.ad.copy(id = ReAgAdId(randomUuid()), lock = ReAgAdLock(randomUuid()))
        return doDbAction(
            "create",
            { dao.create(AdCassandraDTO(new)) },
            { DbAdResponse.success(new) },
            ::errorToAdResponse
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse =
        if (rq.id == ReAgAdId.NONE)
            ID_IS_EMPTY
        else doDbAction(
            "read",
            { dao.read(rq.id.asString()) },
            { found ->
                if (found != null) DbAdResponse.success(found.toAdModel())
                else ID_NOT_FOUND
            },
            ::errorToAdResponse
        )

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse =
        if (rq.ad.id == ReAgAdId.NONE)
            ID_IS_EMPTY
        else {
            val prevLock = rq.ad.lock.asString()
            val new = rq.ad.copy(lock = ReAgAdLock(randomUuid()))
            val dto = AdCassandraDTO(new)
            doDbAction(
                "update",
                { dao.update(dto, prevLock) },
                { updated ->
                    if (updated) DbAdResponse.success(new)
                    else ID_NOT_FOUND
                },
                ::errorToAdResponse
            )
        }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse =
        if (rq.id == ReAgAdId.NONE)
            ID_IS_EMPTY
        else doDbAction(
            "delete",
            { dao.delete(rq.id.asString(), rq.lock.asString()) },
            { deleted ->
                if (deleted) DbAdResponse(null, true)
                else ID_NOT_FOUND
            },
            ::errorToAdResponse
        )


    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse =
        doDbAction(
            "search",
            { dao.search(rq) },
            { found ->
                DbAdsResponse.success(found.map { it.toAdModel() })
            },
            ::errorToAdsResponse
        )

    companion object {
        private val ID_IS_EMPTY = DbAdResponse.error(ReAgError(field = "id", message = "Id is empty"))
        private val ID_NOT_FOUND = DbAdResponse.error(ReAgError(field = "id", message = "Not Found"))
    }
}
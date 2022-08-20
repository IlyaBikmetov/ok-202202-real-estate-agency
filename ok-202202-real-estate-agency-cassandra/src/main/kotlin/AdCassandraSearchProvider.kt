package ru.ibikmetov.kotlin.realestateagency.cassandra

import com.datastax.oss.driver.api.core.cql.AsyncResultSet
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import ru.ibikmetov.kotlin.realestateagency.cassandra.dto.AdCassandraDTO
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgDealSide
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgRentType
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgUserId
import ru.ibikmetov.kotlin.realestateagency.common.repository.DbAdFilterRequest
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.function.BiConsumer

class AdCassandraSearchProvider(
    private val context: MapperContext,
    private val entityHelper: EntityHelper<AdCassandraDTO>
) {
    fun search(filter: DbAdFilterRequest): CompletionStage<Collection<AdCassandraDTO>> {
        var select = entityHelper.selectStart().allowFiltering()

        if (filter.title.isNotBlank()) {
            select = select
                .whereColumn(AdCassandraDTO.COLUMN_TITLE)
                .like(QueryBuilder.literal("%${filter.title}%"))
        }
        if (filter.ownerId != ReAgUserId.NONE) {
            select = select
                .whereColumn(AdCassandraDTO.COLUMN_OWNER_ID)
                .isEqualTo(QueryBuilder.literal(filter.ownerId.asString(), context.session.context.codecRegistry))
        }
        if (filter.dealSide != ReAgDealSide.NONE) {
            select = select
                .whereColumn(AdCassandraDTO.COLUMN_DEAL_SIDE)
                .isEqualTo(QueryBuilder.literal(filter.dealSide.toString(), context.session.context.codecRegistry))
        }
        if (filter.rentType != ReAgRentType.NONE) {
            select = select
                .whereColumn(AdCassandraDTO.COLUMN_RENT_TYPE)
                .isEqualTo(QueryBuilder.literal(filter.rentType.toString(), context.session.context.codecRegistry))
        }
        val asyncFetcher = AsyncFetcher()

        context.session
            .executeAsync(select.build())
            .whenComplete(asyncFetcher)

        return asyncFetcher.stage
    }

    inner class AsyncFetcher : BiConsumer<AsyncResultSet?, Throwable?> {
        private val buffer = mutableListOf<AdCassandraDTO>()
        private val future = CompletableFuture<Collection<AdCassandraDTO>>()
        val stage: CompletionStage<Collection<AdCassandraDTO>> = future

        override fun accept(resultSet: AsyncResultSet?, t: Throwable?) {
            when {
                t != null -> future.completeExceptionally(t)
                resultSet == null -> future.completeExceptionally(IllegalStateException("ResultSet should not be null"))
                else -> {
                    buffer.addAll(resultSet.currentPage().map { entityHelper.get(it, false) })
                    if (resultSet.hasMorePages())
                        resultSet.fetchNextPage().whenComplete(this)
                    else
                        future.complete(buffer)
                }
            }
        }
    }
}
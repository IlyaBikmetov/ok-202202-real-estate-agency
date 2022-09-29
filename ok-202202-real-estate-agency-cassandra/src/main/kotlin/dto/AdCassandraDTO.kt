package ru.ibikmetov.kotlin.realestateagency.cassandra.dto

import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import kotlinx.serialization.Serializable
import ru.ibikmetov.kotlin.realestateagency.common.models.*

@Entity
data class AdCassandraDTO(
    @field:CqlName(COLUMN_ID)
    @field:PartitionKey
    var id: String? = null,
    @field:CqlName(COLUMN_TITLE)
    var title: String? = null,
    @field:CqlName(COLUMN_DESCRIPTION)
    var description: String? = null,
    @field:CqlName(COLUMN_ADDRESS)
    var address: String? = null,
    @field:CqlName(COLUMN_OWNER_ID)
    var ownerId: String? = null,
    @field:CqlName(COLUMN_VISIBILITY)
    var visibility: AdVisibility? = null,
    @field:CqlName(COLUMN_DEAL_SIDE)
    var dealSide: AdDealSide? = null,
    @field:CqlName(COLUMN_RENT_TYPE)
    var rentType: AdRentType? = null,
    @field:CqlName(COLUMN_REALTY_PROPERTY)
    var realtyProperty: AdRealtyProperty? = null,
    @field:CqlName(COLUMN_REALTY)
    var realty: String? = null,
    @field:CqlName(COLUMN_LOCK)
    var lock: String?

) {
    constructor(adModel: ReAgAd) : this(
        id = adModel.id.takeIf { it != ReAgAdId.NONE }?.asString(),
        title = adModel.title.takeIf { it.isNotBlank() },
        description = adModel.description.takeIf { it.isNotBlank() },
        address = adModel.address.takeIf { it.isNotBlank() },
        ownerId = adModel.ownerId.takeIf { it != ReAgUserId.NONE }?.asString(),
        visibility = adModel.visibility.toTransport(),
        dealSide = adModel.dealSide.toTransport(),
        rentType = adModel.rentType.toTransport(),
        realtyProperty = adModel.realtyProperty.toTransport(),
        realty = adModel.realty.toEntity(),
        lock = adModel.lock.takeIf { it != ReAgAdLock.NONE }?.asString()
    )

    fun toAdModel(): ReAgAd =
        ReAgAd(
            id = id?.let { ReAgAdId(it) } ?: ReAgAdId.NONE,
            title = title ?: "",
            description = description ?: "",
            address = address ?: "",
            ownerId = ownerId?.let { ReAgUserId(it) } ?: ReAgUserId.NONE,
            visibility = visibility.fromTransport(),
            dealSide = dealSide.fromTransport(),
            rentType = rentType.fromTransport(),
            realtyProperty = realtyProperty.fromTransport(),
            realty = ReAgAdRealtyEntity.toInternal(realtyProperty!!, realty!!),
            lock = lock?.let { ReAgAdLock(it) } ?: ReAgAdLock.NONE
        )

    companion object {
        const val TABLE_NAME = "ads"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_OWNER_ID = "owner_id"
        const val COLUMN_VISIBILITY = "visibility"
        const val COLUMN_DEAL_SIDE = "deal_side"
        const val COLUMN_RENT_TYPE = "rent_type"
        const val COLUMN_REALTY_PROPERTY = "realty_property"
        const val COLUMN_REALTY = "realty"
        const val COLUMN_LOCK = "lock"

        fun table(keyspace: String, tableName: String) =
            SchemaBuilder
                .createTable(keyspace, tableName)
                .ifNotExists()
                .withPartitionKey(COLUMN_ID, DataTypes.TEXT)
                .withColumn(COLUMN_TITLE, DataTypes.TEXT)
                .withColumn(COLUMN_DESCRIPTION, DataTypes.TEXT)
                .withColumn(COLUMN_ADDRESS, DataTypes.TEXT)
                .withColumn(COLUMN_OWNER_ID, DataTypes.TEXT)
                .withColumn(COLUMN_VISIBILITY, DataTypes.TEXT)
                .withColumn(COLUMN_DEAL_SIDE, DataTypes.TEXT)
                .withColumn(COLUMN_RENT_TYPE, DataTypes.TEXT)
                .withColumn(COLUMN_REALTY_PROPERTY, DataTypes.TEXT)
                .withColumn(COLUMN_REALTY, DataTypes.TEXT)
                .withColumn(COLUMN_LOCK, DataTypes.TEXT)
                .build()

        fun descrIndex(keyspace: String, tableName: String, locale: String = "ru") =
            SchemaBuilder
                .createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(keyspace, tableName)
                .andColumn(COLUMN_DESCRIPTION)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to locale))
                .build()
    }
}
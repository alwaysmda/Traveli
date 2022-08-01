package data.remote.dto

import domain.model.TransactionData
import main.ApplicationClass
import util.EntityMapper
import javax.inject.Inject

class TransactionDataMapper @Inject constructor(private val applicationClass: ApplicationClass, private val transactionMapper: TransactionMapper) : EntityMapper<TransactionDataDto, TransactionData> {
    override fun toDomainModel(dto: TransactionDataDto): TransactionData {
        return TransactionData(
            dto.hasMore,
            transactionMapper.fromEntityList(dto.data),
        )
    }

    override fun toEntity(model: TransactionData): TransactionDataDto =
        TransactionDataDto(
            model.hasMore,
            transactionMapper.fromDomainList(model.transactionList),
        )

    fun fromEntityList(dtoList: List<TransactionDataDto>): List<TransactionData> =
        dtoList.map { toDomainModel(it) }

    fun fromDomainList(entityList: List<TransactionData>): List<TransactionDataDto> =
        entityList.map { toEntity(it) }
}
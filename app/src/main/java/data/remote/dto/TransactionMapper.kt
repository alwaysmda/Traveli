package data.remote.dto

import domain.model.Transaction
import main.ApplicationClass
import util.EntityMapper
import util.extension.separateNumberBy3
import util.extension.translate
import javax.inject.Inject

class TransactionMapper @Inject constructor(val applicationClass: ApplicationClass) : EntityMapper<TransactionDto, Transaction> {
    override fun toDomainModel(dto: TransactionDto): Transaction {
        return Transaction(
            dto.id,
            dto.title,
            dto.amount,
            dto.isPositive,
            dto.isCheckedOut,
            "$${dto.amount.separateNumberBy3().translate()}"
        )
    }

    override fun toEntity(model: Transaction): TransactionDto =
        TransactionDto(
            model.id,
            model.title,
            model.amount,
            model.isPositive,
            model.isCheckedOut,
        )

    fun fromEntityList(dtoList: List<TransactionDto>): List<Transaction> =
        dtoList.map { toDomainModel(it) }

    fun fromDomainList(entityList: List<Transaction>): List<TransactionDto> =
        entityList.map { toEntity(it) }
}
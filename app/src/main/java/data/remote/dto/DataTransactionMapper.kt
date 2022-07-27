package data.remote.dto

import domain.model.DataTransaction
import main.ApplicationClass
import util.EntityMapper
import util.extension.separateNumberBy3
import util.extension.translate
import javax.inject.Inject

class DataDataTransactionMapper @Inject constructor(val applicationClass: ApplicationClass, private val transactionMapper: TransactionMapper) : EntityMapper<DataTransactionDto, DataTransaction> {
    override fun toDomainModel(dto: DataTransactionDto): DataTransaction {
        return DataTransaction(
            dto.balance,
            transactionMapper.fromEntityList(dto.transactionList),
            "$${dto.balance.separateNumberBy3().translate()}"
        )
    }

    override fun toEntity(model: DataTransaction): DataTransactionDto =
        DataTransactionDto(
            model.balance,
            transactionMapper.fromDomainList(model.transactionList),
        )

    fun fromEntityList(dtoList: List<DataTransactionDto>): List<DataTransaction> =
        dtoList.map { toDomainModel(it) }
}
package data.remote.dto

import androidx.core.content.ContextCompat
import com.xodus.traveli.R
import domain.model.Transaction
import main.ApplicationClass
import util.EntityMapper
import util.extension.convertTimestampToDate
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
            ContextCompat.getColor(applicationClass, if (dto.isPositive) R.color.md_green_700 else R.color.md_red_700),
            dto.isCheckedOut,
            "${if (dto.isPositive) "+" else "-"}$${dto.amount.separateNumberBy3().translate()}",
            dto.date,
            convertTimestampToDate(dto.date, "yyyy MMM dd - HH:mm")
        )
    }

    override fun toEntity(model: Transaction): TransactionDto =
        TransactionDto(
            model.id,
            model.title,
            model.amount,
            model.isPositive,
            model.isCheckedOut,
            model.date,
        )

    fun fromEntityList(dtoList: List<TransactionDto>): List<Transaction> =
        dtoList.map { toDomainModel(it) }

    fun fromDomainList(entityList: List<Transaction>): List<TransactionDto> =
        entityList.map { toEntity(it) }
}
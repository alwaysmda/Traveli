package data.remote.dto

import domain.model.Balance
import main.ApplicationClass
import util.EntityMapper
import util.extension.separateNumberBy3
import util.extension.translate
import javax.inject.Inject

class BalanceMapper @Inject constructor(val applicationClass: ApplicationClass) : EntityMapper<ResponseBalanceDto, Balance> {
    override fun toDomainModel(dto: ResponseBalanceDto): Balance {
        return Balance(
            dto.balance,
            "$${dto.balance.separateNumberBy3().translate()}",
        )
    }

    override fun toEntity(model: Balance): ResponseBalanceDto =
        ResponseBalanceDto(
            "",
            MetaDto.getFake(),
            model.balance,
        )

    fun fromEntityList(dtoList: List<ResponseBalanceDto>): List<Balance> =
        dtoList.map { toDomainModel(it) }

    fun fromDomainList(entityList: List<Balance>): List<ResponseBalanceDto> =
        entityList.map { toEntity(it) }
}
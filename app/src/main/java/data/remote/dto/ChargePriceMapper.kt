package data.remote.dto

import domain.model.ChargePrice
import domain.model.PriceItem
import main.ApplicationClass
import util.EntityMapper
import util.extension.separateNumberBy3
import util.extension.translate
import javax.inject.Inject

class ChargePriceMapper @Inject constructor(val applicationClass: ApplicationClass) : EntityMapper<ChargePriceDto, ChargePrice> {
    override fun toDomainModel(dto: ChargePriceDto): ChargePrice {
        val priceList = arrayListOf<PriceItem>()
        dto.priceList.forEach {
            priceList.add(PriceItem("$${it.separateNumberBy3().translate()}", it, false))
        }
        priceList.firstOrNull()?.selected = true
        return ChargePrice(
            priceList,
            dto.isCustom,
            dto.min,
            dto.max,
        )
    }

    override fun toEntity(model: ChargePrice): ChargePriceDto =
        ChargePriceDto(
            model.priceList.map { it.value },
            model.isCustom,
            model.min,
            model.max,
        )

    fun fromEntityList(dtoList: ArrayList<ChargePriceDto>): ArrayList<ChargePrice> =
        ArrayList(dtoList.map { toDomainModel(it) })
}
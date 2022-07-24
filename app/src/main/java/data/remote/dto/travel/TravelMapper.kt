package data.remote.dto.travel

import domain.model.travel.Banner
import domain.model.travel.Travel
import util.EntityMapper

class TravelMapper : EntityMapper<TravelDto, Travel> {
    override fun toDomainModel(dto: TravelDto): Travel {
        return Travel(dto.id, dto.name, dto.image)
    }

    override fun toEntity(model: Travel): TravelDto {
        return TravelDto(model.id, model.name, model.image)
    }

    fun toBanner(bannerDto: BannerDto): Banner {
        return Banner(toDomainModel(bannerDto.banner), bannerDto.subBanner.map { toDomainModel(it) })
    }
}
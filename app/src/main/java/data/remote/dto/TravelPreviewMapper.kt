package data.remote.dto

import domain.model.TravelPreview
import util.EntityMapper
import javax.inject.Inject

class TravelPreviewMapper @Inject constructor() : EntityMapper<TravelPreviewDto, TravelPreview> {
    override fun toDomainModel(dto: TravelPreviewDto): TravelPreview {
        return TravelPreview(dto.id, dto.name, dto.image)
    }

    override fun toEntity(model: TravelPreview): TravelPreviewDto {
        return TravelPreviewDto(model.id, model.name, model.image)
    }

    fun fromEntityList(dtoList: List<TravelPreviewDto>): List<TravelPreview> =
        dtoList.map { toDomainModel(it) }
}
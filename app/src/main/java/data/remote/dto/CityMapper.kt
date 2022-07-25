package data.remote.dto

import domain.model.City
import util.EntityMapper
import javax.inject.Inject

class CityMapper @Inject constructor() : EntityMapper<CityDto, City> {
    override fun toDomainModel(dto: CityDto): City {
        return City(dto.id, dto.name, dto.image)
    }

    override fun toEntity(model: City): CityDto {
        return CityDto(model.id, model.name, model.image)
    }
}
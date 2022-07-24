package data.remote.dto.country

import domain.model.Country
import util.EntityMapper
import javax.inject.Inject

class CountryMapper @Inject constructor() : EntityMapper<CountryDto, Country> {
    override fun toDomainModel(dto: CountryDto): Country {
        return Country(dto.id, dto.name, dto.image)
    }

    override fun toEntity(model: Country): CountryDto {
        return CountryDto(model.id, model.name, model.image)
    }

    fun fromEntityList(dtoList: ArrayList<CountryDto>): ArrayList<Country> {
        return ArrayList(dtoList.map { toDomainModel(it) })
    }
}
package data.remote.dto

import domain.model.Travel
import util.EntityMapper
import javax.inject.Inject

class TravelMapper @Inject constructor(
    private val travelDetailMapper: TravelDetailMapper,
    private val cityMapper: CityMapper,
    private val countryMapper: CountryMapper,
    private val userMapper: UserMapper,
) : EntityMapper<TravelDto, Travel> {


    override fun toDomainModel(dto: TravelDto): Travel {
        return Travel(
            dto.id,
            dto.name,
            dto.image,
            countryMapper.toDomainModel(dto.country),
            dto.isBookmarked,
            dto.price,
            dto.details.map { travelDetailMapper.toDomainModel(it) }.toMutableList(),
            dto.cityList.map { cityMapper.toDomainModel(it) },
            userMapper.toDomainModel(dto.user)


        )
    }

    override fun toEntity(model: Travel): TravelDto {
        return TravelDto(
            model.id,
            model.name,
            model.image,
            countryMapper.toEntity(model.country),
            model.isBookmarked,
            model.price,
            model.details.map { travelDetailMapper.toEntity(it) },
            model.cityList.map { cityMapper.toEntity(it) },
            userMapper.toEntity(model.user)
        )
    }


}
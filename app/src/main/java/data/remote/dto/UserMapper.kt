package data.remote.dto

import domain.model.User
import util.EntityMapper
import javax.inject.Inject

class UserMapper @Inject constructor(private val contactMapper: ContactMapper, private val countryMapper: CountryMapper) : EntityMapper<UserDto, User> {
    override fun toDomainModel(dto: UserDto): User {
        return User(
            dto.id,
            dto.name,
            dto.avatar,
            dto.bio,
            dto.cover,
            countryMapper.toDomainModel(dto.country),
            dto.city,
            "${dto.city}, ${dto.country.name}",
            contactMapper.toDomainModel(dto.contact)
        )
    }

    override fun toEntity(model: User): UserDto =
        UserDto(
            model.id,
            model.name,
            model.avatar,
            model.bio,
            model.cover,
            countryMapper.toEntity(model.country),
            model.city,
            contactMapper.toEntity(model.contact)
        )

    fun fromEntityList(dtoList: ArrayList<UserDto>): ArrayList<User> =
        ArrayList(dtoList.map { toDomainModel(it) })
}
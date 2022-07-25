package data.remote.dto

import data.remote.dto.country.CountryDto
import domain.model.User
import util.EntityMapper
import javax.inject.Inject

class UserMapper @Inject constructor(val contactMapper: ContactMapper) : EntityMapper<UserDto, User> {
    override fun toDomainModel(dto: UserDto): User {
        return User(
            dto.id,
            dto.name,
            dto.avatar,
            dto.bio,
            dto.cover,
            "${dto.country.name}, ${dto.city}",
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
            CountryDto.getFake(),
            "",
            contactMapper.toEntity(model.contact)
        )

    fun fromEntityList(dtoList: ArrayList<UserDto>): ArrayList<User> =
        ArrayList(dtoList.map { toDomainModel(it) })
}
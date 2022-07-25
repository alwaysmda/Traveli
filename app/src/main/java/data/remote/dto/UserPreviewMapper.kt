package data.remote.dto

import domain.model.UserPreview
import main.ApplicationClass
import util.EntityMapper
import javax.inject.Inject

class UserPreviewMapper @Inject constructor(val applicationClass: ApplicationClass) : EntityMapper<UserPreviewDto, UserPreview> {
    override fun toDomainModel(dto: UserPreviewDto): UserPreview {
        return UserPreview(
            dto.id,
            dto.name,
            dto.avatar,
        )
    }

    override fun toEntity(model: UserPreview): UserPreviewDto =
        UserPreviewDto(
            model.id,
            model.name,
            model.avatar,
        )

    fun fromEntityList(dtoList: List<UserPreviewDto>): List<UserPreview> =
        dtoList.map { toDomainModel(it) }
}
package data.remote.dto

import com.xodus.templatefive.R
import domain.model.Stat
import main.ApplicationClass
import util.EntityMapper
import javax.inject.Inject

class StatMapper @Inject constructor(val applicationClass: ApplicationClass) : EntityMapper<StatDto, Stat> {
    override fun toDomainModel(dto: StatDto): Stat {
        var title = applicationClass.m.title
        var value = dto.value
        var icon = R.drawable.ic_launcher
        return Stat(
            title,
            value,
            icon
        )
    }

    override fun toEntity(model: Stat): StatDto =
        StatDto(
            model.title,
            model.value,
        )

    fun fromEntityList(dtoList: ArrayList<StatDto>): ArrayList<Stat> =
        ArrayList(dtoList.map { toDomainModel(it) })
}
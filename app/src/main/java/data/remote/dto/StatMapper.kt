package data.remote.dto

import com.xodus.templatefive.R
import domain.model.Stat
import main.ApplicationClass
import okhttp3.internal.toLongOrDefault
import util.EntityMapper
import util.extension.separateNumberBy3
import util.extension.translate
import javax.inject.Inject

class StatMapper @Inject constructor(val applicationClass: ApplicationClass) : EntityMapper<StatDto, Stat> {
    override fun toDomainModel(dto: StatDto): Stat {
        var title = ""
        var icon = R.drawable.ic_launcher
        when (dto.type) {
            "cities"    -> {
                title = applicationClass.m.cities
                icon = R.drawable.ic_compass
            }
            "countries" -> {
                title = applicationClass.m.countries
                icon = R.drawable.ic_compass
            }
        }
        val value = dto.value.separateNumberBy3().translate()
        return Stat(
            title,
            value,
            icon
        )
    }

    override fun toEntity(model: Stat): StatDto =
        StatDto(
            model.title,
            model.value.toLongOrDefault(0),
        )

    fun fromEntityList(dtoList: ArrayList<StatDto>): ArrayList<Stat> =
        ArrayList(dtoList.map { toDomainModel(it) })
}
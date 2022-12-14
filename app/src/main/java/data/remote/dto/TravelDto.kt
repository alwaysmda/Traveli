package data.remote.dto


import data.remote.dto.TravelPreviewDto.Companion.carabianSeeImage
import kotlin.random.Random

data class TravelDto(
    val id: Int,
    val name: String,
    val image: String,
    val country: CountryDto,
    val isBookmarked: Boolean,
    val price: Long,
    val details: List<TravelDetailDto>,
    val cityList: List<CityDto>,
    val tagList: List<String>,
    val user: UserPreviewDto

) {
    companion object {
        fun getFake() = TravelDto(
            Random.nextInt(1000), "Caribbean Sea", carabianSeeImage, CountryDto(0, "Iran", ""), false, 2000, TravelDetailDto.getFakes(), CityDto.getFakes(),
            listOf("tehran", "travel", "vacation", "trip", "off_road", "sea", "swimming"), UserPreviewDto.getFake()
        )

        fun getFake(size: Int = 10): ArrayList<TravelDto> {
            val list = arrayListOf<TravelDto>()
            repeat(size) {
                list.add(getFake())
            }
            return list
        }
    }

}

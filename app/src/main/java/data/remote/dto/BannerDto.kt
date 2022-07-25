package data.remote.dto

data class BannerDto(
    val banner: TravelDto,
    val subBanner: List<TravelDto>
)
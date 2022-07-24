package data.remote.dto.travel

data class BannerDto(
    val banner: TravelDto,
    val subBanner: List<TravelDto>
)
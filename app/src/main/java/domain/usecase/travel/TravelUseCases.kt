package domain.usecase.travel

data class TravelUseCases(
    val getTrending: GetTrending,
    val getBanner: GetBanner,
    val getNewTravel: GetNewTravel,
    val getTravel:GetTravel,
    val getTravelDetail:GetTravelDetail
)

package domain.usecase.travel

data class TravelUseCases(
    val getTrendingListUseCase: GetTrendingListUseCase,
    val getBannerUseCase: GetBannerUseCase,
    val getNewTravelListUseCase: GetNewTravelListUseCase,
    val searchTravelsUseCase: SearchTravelsUseCase,
    val getTravelDetailUseCase: GetTravelDetailUseCase
)

package domain.usecase.travel

import domain.repository.TraveliRepository

class GetBanner(private val traveliRepo:TraveliRepository) {
    operator fun invoke() = traveliRepo.getBanner()
}
package domain.usecase.travel

import domain.repository.TraveliRepository

class GetTravel(private val traveliRepo: TraveliRepository) {

    operator fun invoke(isTrending: Boolean = true, isNew: Boolean = true) = traveliRepo.getTravel(isTrending,isNew)
}
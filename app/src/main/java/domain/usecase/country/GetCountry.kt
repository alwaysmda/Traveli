package domain.usecase.country

import domain.repository.TraveliRepository

class GetCountry(private val repo:TraveliRepository) {
    operator fun invoke() = repo.getCountries()
}
package data.remote.dto.country

import kotlin.random.Random

data class CountryDto(
    val id: Int,
    val name: String,
    val image: String,
) {
    companion object {
        val takhtJamshidImage = "https://rashintravel.com/wp-content/uploads/2019/12/Day-17-Persepolis.jpg"
        fun getFake() = CountryDto(Random.nextInt(), "Iran", takhtJamshidImage)
        fun getFakes(): ArrayList<CountryDto> {
            val countries = ArrayList<CountryDto>()
            repeat(10) {
                countries.add(getFake())
            }
            return countries
        }
    }
}

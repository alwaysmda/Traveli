package data.remote.dto

import kotlin.random.Random

data class CountryDto(
    val id: Long,
    val name: String,
    val image: String,
) {
    companion object {
        val takhtJamshidImage = "https://rashintravel.com/wp-content/uploads/2019/12/Day-17-Persepolis.jpg"
        fun getFake() = CountryDto(Random.nextLong(1, 10000), "Iran", takhtJamshidImage)
        fun getFakes(): ArrayList<CountryDto> {
            val countries = ArrayList<CountryDto>()
            repeat(10) {
                countries.add(getFake())
            }
            return countries
        }
    }
}

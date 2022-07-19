package domain.model

import kotlin.random.Random

data class City(
    val id: Int,
    val name: String,
    val image: String
) {
    companion object {
        val campnou = "https://www.stern.de/digital/online/spotify-wird-hauptsponsor-beim-fc-barcelona----der-grund-dafuer-ist-recht-simpel-31705516.html"
        fun getFake() = City(Random.nextInt(), "Barcelona", campnou)
        fun getFakes(): List<City> {
            val cities = mutableListOf<City>()
            repeat(5) {
                cities.add(getFake())
            }
            return cities
        }


    }

}
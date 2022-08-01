package data.remote.dto

import kotlin.random.Random

data class CityDto(
    val id: Long,
    val name: String,
    val image: String
) {
    companion object {
        fun getFake() = CityDto(Random.nextLong(100000), "Barcelona", "https://www.here.com/sites/g/files/odxslz256/files/styles/blog_post_xl/public/2022-06/barcelona-blog.jpg?itok=_1W7-L44")
        fun getFakes(): ArrayList<CityDto> {
            val cityList = arrayListOf<CityDto>()
            repeat(10) {
                cityList.add(getFake())
            }
            return cityList
        }
    }

}
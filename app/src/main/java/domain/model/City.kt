package domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class City(
    val id: Long,
    val name: String,
    val image: String
) : Parcelable {
    companion object {
        val campnou = "https://www.stern.de/digital/online/spotify-wird-hauptsponsor-beim-fc-barcelona----der-grund-dafuer-ist-recht-simpel-31705516.html"
        fun getFake() = City(Random.nextLong(100000), "Barcelona", campnou)
        fun getFakes(): List<City> {
            val cities = mutableListOf<City>()
            repeat(5) {
                cities.add(getFake())
            }
            return cities
        }
    }
}
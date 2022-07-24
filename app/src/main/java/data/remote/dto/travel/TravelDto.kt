package data.remote.dto.travel

import domain.model.travel.Travel
import kotlin.random.Random

data class TravelDto(
    val id: Int,
    val name: String,
    val image: String
) {
    companion object {
        fun getFake() = TravelDto(Random.nextInt(1000), "Caribbean Sea", Travel.carabianSeeImage)
        fun getFake(size: Int = 10): ArrayList<TravelDto> {
            val list = arrayListOf<TravelDto>()
            repeat(size) {
                list.add(getFake())
            }
            return list
        }
    }

}

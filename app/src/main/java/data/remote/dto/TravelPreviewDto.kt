package data.remote.dto

import kotlin.random.Random

data class TravelPreviewDto(
    val id: Int,
    val name: String,
    val image: String,
) {

    companion object {
        const val carabianSeeImage = "https://www.wildearth-travel.com/get-image-version/verybig/uploads/caribbean_sea_11_days_(fram)_picture.jpg"

        fun getFake() = TravelPreviewDto(Random.nextInt(1000), "Caribbean Sea", carabianSeeImage)
        fun getFake(size: Int = 20): ArrayList<TravelPreviewDto> {
            val list = arrayListOf<TravelPreviewDto>()
            repeat(size) {
                list.add(getFake())
            }
            return list
        }
    }

}
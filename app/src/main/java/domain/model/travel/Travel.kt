package domain.model.travel

import kotlin.random.Random

data class Travel(
    val id: Int,
    val name: String,
    val image: String,
) {
    companion object {
        const val carabianSeeImage = "https://www.wildearth-travel.com/get-image-version/verybig/uploads/caribbean_sea_11_days_(fram)_picture.jpg"

        fun getFake() = Travel(Random.nextInt(1000), "Caribbean Sea", carabianSeeImage)
        fun getFake(size: Int = 10): List<Travel> {
            val list = arrayListOf<Travel>()
            repeat(size) {
                list.add(getFake())
            }
            return list
        }
    }
}

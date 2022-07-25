package data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class UserPreviewDto(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("avatar") var avatar: String,
) {
    companion object {
        fun getFake(): UserPreviewDto {
            return UserPreviewDto(Random.nextLong(1, 10000), "Denis Trico", "https://upload.wikimedia.org/wikipedia/commons/2/23/Dennis_Ritchie_2011.jpg")
        }

        fun getFakeList(size: Int = 10): ArrayList<UserPreviewDto> {
            val list = arrayListOf<UserPreviewDto>()
            repeat(size) {
                list.add(getFake())
            }
            return list
        }
    }
}
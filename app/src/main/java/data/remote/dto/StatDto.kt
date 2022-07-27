package data.remote.dto

import com.google.gson.annotations.SerializedName

data class StatDto(
    @SerializedName("type") var type: String,
    @SerializedName("value") var value: Long,
) {
    companion object {
        fun getFake(): StatDto {
            return StatDto("cities", 10000)
        }

        fun getFakeList(size: Int = 4): ArrayList<StatDto> {
            val list = arrayListOf<StatDto>()
            repeat(size) {
                list.add(getFake())
            }
            return list
        }
    }
}
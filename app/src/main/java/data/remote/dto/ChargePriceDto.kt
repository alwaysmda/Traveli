package data.remote.dto

import com.google.gson.annotations.SerializedName

data class ChargePriceDto(
    @SerializedName("prices") var priceList: List<Long>,
    @SerializedName("is_custom") var isCustom: Boolean,
    @SerializedName("max") var max: Long,
    @SerializedName("min") var min: Long,
) {
    companion object {
        fun getFake(): ChargePriceDto {
            return ChargePriceDto(listOf(1000, 2000, 5000, 10000), true, 100000, 1000)
        }

        fun getFakeList(size: Int = 4): ArrayList<ChargePriceDto> {
            val list = arrayListOf<ChargePriceDto>()
            repeat(size) {
                list.add(getFake())
            }
            return list
        }
    }
}
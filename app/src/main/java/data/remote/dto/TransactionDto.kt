package data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class TransactionDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("amount") val amount: Long,
    @SerializedName("is_positive") val isPositive: Boolean,
    @SerializedName("is_checked_out") val isCheckedOut: Boolean,
) {
    companion object {
        fun getFake(): TransactionDto = TransactionDto(
            Random.nextLong(1, 10000),
            arrayOf("Purchase travel package", "Change balance", "Checkout", "Sell travel package").random(),
            Random.nextLong(1, 100) * 100,
            Random.nextBoolean(),
            Random.nextBoolean(),
        )

        fun getFakeList(size: Int = 10): ArrayList<TransactionDto> {
            val list = arrayListOf<TransactionDto>()
            repeat(size) {
                list.add(getFake())
            }
            return list
        }
    }
}

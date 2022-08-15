package domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChargePrice(
    val priceList: List<PriceItem>,
    val isCustom: Boolean,
    val min: Long,
    val max: Long,
) : Parcelable {
    companion object {
        fun List<ChargePrice>.cloned() = ArrayList(map { it.copy() })
    }
}
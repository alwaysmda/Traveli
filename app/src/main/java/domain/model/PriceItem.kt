package domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PriceItem(
    val title: String,
    val value: Long,
    var selected: Boolean,
) : Parcelable {
    companion object {
        fun List<PriceItem>.cloned() = ArrayList(map { it.copy() })
    }
}
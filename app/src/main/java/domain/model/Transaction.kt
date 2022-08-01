package domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id: Long,
    val title: String,
    val amount: Long,
    val isPositive: Boolean,
    val color: Int,
    val isCheckedOut: Boolean,
    val amountString: String,
    val date: Long,
    val dateString: String,
) : Parcelable {
    companion object {
        const val LOADING_ID = 0L
        const val FAILURE_ID = -1L
        fun getLoadingItem() = Transaction(LOADING_ID, "", 0, false, 0, false, "", 0, "")
        fun getFailureItem() = Transaction(FAILURE_ID, "", 0, false, 0, false, "", 0, "")
        fun List<Transaction>.cloned() = ArrayList(map { it.copy() })
    }
}
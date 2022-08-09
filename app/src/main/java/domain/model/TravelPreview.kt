package domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TravelPreview(
    val id: Long,
    val name: String,
    val image: String,
) : Parcelable {
    companion object {
        const val ADD_ID = 0L
        const val LOADING_ID = -1L
        const val FAILURE_ID = -2L
        fun getAddItem() = TravelPreview(ADD_ID, "", "")
        fun getLoadingItem() = TravelPreview(LOADING_ID, "", "")
        fun getFailureItem() = TravelPreview(FAILURE_ID, "", "")
        fun List<TravelPreview>.cloned() = ArrayList(this.map { it.copy() })
    }
}
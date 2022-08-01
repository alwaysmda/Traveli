package domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TravelPreview(
    val id: Int,
    val name: String,
    val image: String,
) : Parcelable {
    companion object {
        const val ADD_ID = 0
        fun getAddItem() = TravelPreview(ADD_ID, "", "")
        fun List<TravelPreview>.cloned() = ArrayList(this.map { it.copy() })
    }


}

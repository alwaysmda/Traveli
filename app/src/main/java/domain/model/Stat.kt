package domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stat(
    val title: String,
    val value: String,
    @DrawableRes val icon: Int,
) : Parcelable {
    //    val title: String
    //        get() {
    //            val m = ApplicationClass.getInstance().m
    //            return when (type) {
    //                "cities" -> m.cities
    //                "countries" -> m.countries
    //                "travels" -> m.travels
    //                else     -> ""
    //            }
    //        }
    companion object {
        fun List<Stat>.cloned() = ArrayList(map { it.copy() })
    }
}
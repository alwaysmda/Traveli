package domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.xodus.templatefive.R
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
        fun getFake(): Stat = Stat("cities", "10,000", R.drawable.ic_compass)
        fun getFakeList(size: Int = 10): ArrayList<Stat> {
            val list = arrayListOf<Stat>()
            repeat(size) {
                list.add(getFake())
            }
            return list
        }
    }
}
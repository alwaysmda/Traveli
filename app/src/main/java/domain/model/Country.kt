package domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val id: Long,
    val name: String,
    val image: String
) : Parcelable
